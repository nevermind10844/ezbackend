$(document).ready(function() {
	$('#dd_creepingAvg').on('change', creepingAvgChanged);
	$('#dd_valueRange').on('change', valueRangeChanged);

	$('.invalidate-stat-canvas').on('change', statCanvasInvalidated);

	$('#frm_statCanvasSettings').on('submit', statCanvasFormSubmitted);

	$(document).ready(function() {
		$('button[data-bs-toggle="tab"]').on('show.bs.tab', function(e) {
			let target = $(e.target).data("bs-target")
			switch (target) {
				case "#general_stats":
					statOptions.preventInvalidation = false;
					break;
				case "#forecast_stats":
					forecastOptions.preventInvalidation = false;
					break;
			}
		});

		$('button[data-bs-toggle="tab"]').on('shown.bs.tab', function(e) {
			let target = $(e.target).data("bs-target")
			switch (target) {
				case "#general_stats":
					if (!statOptions.preventInvalidation) {
						resizeStatCanvas();
						drawStatCanvas();
					}
					break;
				case "#forecast_stats":
					if (!forecastOptions.preventInvalidation) {
						resizeForecastCanvas();
						drawForecastCanvas();
					}
					break;
			}
		});

		$('button[data-bs-toggle="tab"]').on('hide.bs.tab', function(e) {
			let target = $(e.target).data("bs-target")
			switch (target) {
				case "#general_stats":
					statOptions.preventInvalidation = true;
					break;
				case "#forecast_stats":
					forecastOptions.preventInvalidation = true;
					break;
			}
		});
	});
});

function statCanvasFormSubmitted(e) {
	e.preventDefault();
}

function statCanvasInvalidated(e) {
	$.ajax({
		url: $('#frm_statCanvasSettings').attr('action'),
		data: $('#frm_statCanvasSettings').serialize()
	}).done(function(response, e) {
		statOptions.splitList = response;
		drawStatCanvas();
	});
}

function creepingAvgChanged(e) {
	statOptions.creepingAvg = $(this).val();
	drawStatCanvas();
}

function valueRangeChanged(e) {
	statOptions.valueRange = $(this).val();
	drawStatCanvas();
}

$(document).ready(function() {
	window.addEventListener("resize", windowResized);
	if (!statOptions.preventInvalidation) {
		resizeStatCanvas();
		drawStatCanvas();
	}
	if (!forecastOptions.preventInvalidation) {
		resizeForecastCanvas();
		drawForecastCanvas();
	}
});

function windowResized(e) {
	if (!statOptions.preventInvalidation) {
		resizeStatCanvas();
		drawStatCanvas();
	}
	if (!forecastOptions.preventInvalidation) {
		resizeForecastCanvas();
		drawForecastCanvas();
	}
}

function resizeStatCanvas() {
	let statCanvas = document.getElementById("meter_stat");
	statCanvas.width = $('#meter_stat').width();
	statCanvas.height = $('#meter_stat').height();
}

function resizeForecastCanvas() {
	let forecastCanvas = document.getElementById("meter_forecast");
	forecastCanvas.width = $('#meter_forecast').width();
	forecastCanvas.height = $('#meter_forecast').height();
}

function drawStatCanvas() {
	console.log("drawing Stat-Canvas");
	statOptions.avg = 0;
	statOptions.splitPeriod = $('#dd_splitPeriod').val();
	let statCanvas = document.getElementById("meter_stat");
	let statCtx = statCanvas.getContext("2d");
	

	
	let valueRange = statOptions.valueRange;
	let highestValue = Math.max.apply(Math, statOptions.splitList.map(function(o) { return o[valueRange]; }))
	if(highestValue > statOptions.highestPerWeek){
		$.ajax({
			url: $('#frm_statCanvasSettings').attr('action'),
			data: {
				highestValue: highestValue,
				valueRange: statOptions.valueRange,
				splitPeriod: statOptions.splitPeriod
			}
		}).done(function(response, e) {
			statOptions.splitList = response;
			drawStatCanvas();
		});
	}
	
	statCtx.clearRect(0, 0, statCanvas.width, statCanvas.height);
	drawStatContext(statCanvas);
	drawStatSticks(statCanvas);
	//drawStatLine(statCanvas);
}

function drawForecastCanvas() {
	console.log("drawing Forecast-Canvas");
	let forecastCanvas = document.getElementById("meter_forecast");
	let forecastCtx = forecastCanvas.getContext("2d");
	forecastCtx.clearRect(0, 0, forecastCanvas.width, forecastCanvas.height);
	drawForecastContext(forecastCanvas);
	drawForecastSticks(forecastCanvas);
	drawForecastLine(forecastCanvas);
}

function niceRoundMax(actualMax) {
	let magnitude = Math.pow(10, Math.ceil(Math.log10(actualMax)) - 1);
	let roundedMax = Math.ceil(actualMax / magnitude) * magnitude;
	return roundedMax;
}

function calculateDivisions(maxVal) {
	// Set of possible "nice" intervals
	const intervals = [1, 2, 5, 10, 20, 50, 100, 200, 500, 1000, 5000, 10000];
	intervals.reverse();
	
	// Try each interval and find the one that gives between 5 and 10 divisions
	for (let interval of intervals) {
		let divisions = Math.ceil(maxVal / interval);
		if (divisions >= 5 && divisions <= 10) {
			return divisions;
		}
	}
	
	// If no suitable interval is found, default to the largest one
	let interval = intervals[0];
	let divisions = Math.ceil(maxVal / interval);
	return divisions;
}

function drawStatContext(statCanvas) {
	let statCtx = statCanvas.getContext("2d");
	statCtx.fillStyle = "lightgrey";
	let highestValue = Math.max.apply(Math, statOptions.splitList.map(function(o) { return o[statOptions.valueRange]; }))
	
	
	
	let roundMax = niceRoundMax(highestValue);
		
	let lowestValue = 0;// Math.min.apply(Math, splitList.map(function (o) {return o[valueRange];}))
	
	statOptions.numberOfDivisions = calculateDivisions(roundMax);
	console.log(roundMax, statOptions.numberOfDivisions)

	let drawHeight = statCanvas.height - statOptions.bottomStripHeight;
	let rawDivision = roundMax / (statOptions.numberOfDivisions);
	let divisions = new Array();

	for (var i = 0; i <= statOptions.numberOfDivisions; i++) {
		let value = rawDivision * i + lowestValue;
		divisions[i] = value;
	}
	
	for (var i = 0; i < divisions.length; i++) {
		let height = scale(divisions[i], lowestValue, roundMax, 0, drawHeight);
		statCtx.beginPath();
		let currentHeight = Math.round(drawHeight - height);
		statCtx.moveTo(0, currentHeight + statOptions.verticalOffset);
		statCtx.fillStyle = 'rgb(46, 64, 87)';
		statCtx.font = '11px Segoe UI';
		if (i == 0 && statOptions.meteringUnit) {
			statCtx.fillText(statOptions.meteringUnit, 0, currentHeight + 23);
		}
		statCtx.fillText((Math.round(divisions[i] * 100) / 100).toFixed(2), 0, Math.round(drawHeight - height) + 11);
		statCtx.lineTo(statCanvas.width, Math.round(drawHeight - height + statOptions.verticalOffset));
		statCtx.lineWidth = 0.1;
		statCtx.stroke();
	}
}

function drawForecastContext(forecastCanvas) {
	let forecastCtx = forecastCanvas.getContext("2d");
	forecastCtx.fillStyle = "lightgrey";
	let highestValue = Math.max.apply(Math, forecastOptions.forecastReadings.map(function(o) { return o[forecastOptions.valueRange]; }))
	let lowestValue = Math.min.apply(Math, forecastOptions.forecastReadings.map(function(o) { return o[forecastOptions.valueRange]; }))
	let margin = lowestValue * forecastOptions.marginFactor;
	lowestValue -= margin;
	highestValue += margin;

	let drawHeight = forecastCanvas.height - forecastOptions.bottomStripHeight;
	let rawDivision = (highestValue - lowestValue) / (forecastOptions.numberOfDivisions - 1);
	let divisions = new Array();

	for (var i = 0; i < forecastOptions.numberOfDivisions; i++) {
		let value = rawDivision * i + lowestValue;
		divisions[i] = value;
	}

	for (var i = 0; i < divisions.length; i++) {
		let height = scale(divisions[i], lowestValue, highestValue, 0, drawHeight);
		forecastCtx.beginPath();
		let currentHeight = Math.round(drawHeight - height);
		forecastCtx.moveTo(0, currentHeight + forecastOptions.verticalOffset);
		forecastCtx.fillStyle = 'rgb(46, 64, 87)';
		forecastCtx.font = '10px Segoe UI';
		if (i == 0 && forecastOptions.meteringUnit) {
			forecastCtx.fillText(forecastOptions.meteringUnit, 0, currentHeight + 23);
		}
		forecastCtx.fillText((Math.round(divisions[i] * 100) / 100).toFixed(2), 0, Math.round(drawHeight - height) + 11);
		forecastCtx.lineTo(forecastCanvas.width, Math.round(drawHeight - height + forecastOptions.verticalOffset));
		forecastCtx.lineWidth = 0.1;
		forecastCtx.stroke();
	}
}

function drawStatSticks(statCanvas) {
	let statCtx = statCanvas.getContext("2d");
	statCtx.fillStyle = "lightgrey";
	let sectionWidth = (statCanvas.width - statOptions.leftColumnWidth - statOptions.rightSpacing) / statOptions.splitList.length;
	let highestValue = Math.max.apply(Math, statOptions.splitList.map(function(o) { return o[statOptions.valueRange]; }))
	highestValue = niceRoundMax(highestValue);
	let lowestValue = 0;
	let paintableWidth = statCanvas.width - statOptions.leftColumnWidth - statOptions.rightSpacing;

	let drawHeight = statCanvas.height - statOptions.bottomStripHeight;

	let prevXStart = undefined;
	let prevHeight = undefined;

	let minReadingTime = Math.min.apply(Math, statOptions.splitList.map(function(o) { return o.endMinutes }));
	let maxReadingTime = Math.max.apply(Math, statOptions.splitList.map(function(o) { return o.endMinutes }));

	$(statOptions.splitList).each(function(_, element) {

		//let xStart = index * sectionWidth;

		statOptions.actualStickWidth = sectionWidth < statOptions.stickWidth ? sectionWidth : statOptions.stickWidth;

		//xStart = Math.floor(xStart + ((sectionWidth - statOptions.actualStickWidth) / 2));
		let xStart = scale(element.endMinutes, minReadingTime, maxReadingTime, 0, paintableWidth) - statOptions.actualStickWidth / 2;

		let realValue = element[statOptions.valueRange];
		statOptions.avg += realValue;

		let height = scale(realValue, lowestValue, highestValue, 0, drawHeight);

		let date = new Date(element.endDate);
		let weekdayOptions = { weekday: 'short' };
		let weekDay = new Intl.DateTimeFormat('de-DE', weekdayOptions).format(date);
		let monthOptions = { month: 'long' };
		let month = new Intl.DateTimeFormat('de-DE', monthOptions).format(date);

		statCtx.fillStyle = 'rgba(46, 64, 87, 0.78)';
		statCtx.fillRect(xStart + statOptions.leftColumnWidth, drawHeight + statOptions.verticalOffset, statOptions.actualStickWidth, -height - statOptions.verticalOffset);

		if (statOptions.splitPeriod == 1 && date.getDay() <= 0)
			statCtx.fillStyle = 'rgb(200, 64, 87)';
		else
			statCtx.fillStyle = 'rgb(46, 64, 87)';

		statCtx.font = '10px Segoe UI';
		statCtx.save();
		statCtx.translate(xStart + statOptions.leftColumnWidth, drawHeight + statOptions.verticalOffset + 6);
		statCtx.rotate(Math.PI / 4);
		let stickText = statOptions.splitPeriod == 2 ? "KW " + date.getWeek() + " " + date.getFullYear() : (statOptions.splitPeriod == 3 ? month + " " + date.getFullYear() : weekDay + " " + date.toLocaleDateString());
		statCtx.fillText(stickText, 0, 0);
		statCtx.restore();
	});

	statOptions.avg /= statOptions.splitList.length;
}

function drawForecastSticks(forecastCanvas) {
	let forecastCtx = forecastCanvas.getContext("2d");
	forecastCtx.fillStyle = "lightgrey";
	let paintableWidth = forecastCanvas.width - forecastOptions.leftColumnWidth - forecastOptions.rightSpacing;
	let highestValue = Math.max.apply(Math, forecastOptions.forecastReadings.map(function(o) { return o[forecastOptions.valueRange]; }))
	let lowestValue = Math.min.apply(Math, forecastOptions.forecastReadings.map(function(o) { return o[forecastOptions.valueRange]; }))
	let margin = lowestValue * forecastOptions.marginFactor;
	highestValue += margin;
	lowestValue -= margin;

	let minReadingTime = Math.min.apply(Math, forecastOptions.forecastReadings.map(function(o) { return o.readingMinutes }));
	let maxReadingTime = Math.max.apply(Math, forecastOptions.forecastReadings.map(function(o) { return o.readingMinutes }));

	minReadingTime -= 43200;
	maxReadingTime += 43200;

	let drawHeight = forecastCanvas.height - forecastOptions.bottomStripHeight;

	$(forecastOptions.forecastReadings).each(function(index, element) {
		forecastCtx.fillStyle = 'rgb(46, 64, 87)';

		let xStart = scale(element.readingMinutes, minReadingTime, maxReadingTime, 0, paintableWidth) - forecastOptions.actualStickWidth / 2;

		forecastOptions.actualStickWidth = forecastOptions.stickWidth;

		let realValue = element[forecastOptions.valueRange];
		forecastOptions.avg += realValue;

		let height = scale(realValue, lowestValue, highestValue, 0, drawHeight);

		let date = new Date(element.readingTime);
		let weekdayOptions = { weekday: 'short' };
		let weekDay = new Intl.DateTimeFormat('de-DE', weekdayOptions).format(date);
		let monthOptions = { month: 'long' };
		let month = new Intl.DateTimeFormat('de-DE', monthOptions).format(date);

		forecastCtx.fillStyle = element.faint ? 'rgba(46, 64, 87, 0.2)' : 'rgba(46, 64, 87, 0.6)';
		forecastCtx.fillRect(xStart + forecastOptions.leftColumnWidth, drawHeight + forecastOptions.verticalOffset, forecastOptions.actualStickWidth, -height - forecastOptions.verticalOffset);
		forecastCtx.fillStyle = 'rgb(46, 64, 87)';
		forecastCtx.font = '10px Segoe UI';
		forecastCtx.save();
		forecastCtx.translate(xStart + forecastOptions.leftColumnWidth, drawHeight + forecastOptions.verticalOffset + 6);
		forecastCtx.rotate(Math.PI / 4);
		let stickText = weekDay + " " + date.toLocaleDateString();
		forecastCtx.fillText(stickText, 0, 0);
		forecastCtx.restore();

	});

	forecastOptions.avg /= forecastOptions.forecastReadings.length;
}

function drawForecastLine(forecastCanvas) {
	let forecastCtx = forecastCanvas.getContext("2d");
	forecastCtx.fillStyle = "lightgrey";
	let paintableWidth = forecastCanvas.width - forecastOptions.leftColumnWidth - forecastOptions.rightSpacing;
	let highestValue = Math.max.apply(Math, forecastOptions.forecastReadings.map(function(o) { return o[forecastOptions.valueRange]; }))
	let lowestValue = Math.min.apply(Math, forecastOptions.forecastReadings.map(function(o) { return o[forecastOptions.valueRange]; }))
	let margin = lowestValue * forecastOptions.marginFactor;
	let maringHighestValue = highestValue + margin;
	let marginLowestValue = lowestValue - margin;

	let minReadingTime = Math.min.apply(Math, forecastOptions.forecastReadings.map(function(o) { return o.readingMinutes }));
	let maxReadingTime = Math.max.apply(Math, forecastOptions.forecastReadings.map(function(o) { return o.readingMinutes }));

	let marginMinReadingTime = minReadingTime - 43200;
	let marginMaxReadingTime = maxReadingTime + 43200;

	let xStart = scale(minReadingTime, marginMinReadingTime, marginMaxReadingTime, 0, paintableWidth) - forecastOptions.actualStickWidth / 2;
	let xEnd = scale(maxReadingTime, marginMinReadingTime, marginMaxReadingTime, 0, paintableWidth) - forecastOptions.actualStickWidth / 2;

	let drawHeight = forecastCanvas.height - forecastOptions.bottomStripHeight;

	let yStart = scale(lowestValue, marginLowestValue, maringHighestValue, 0, drawHeight);
	let yEnd = scale(highestValue, marginLowestValue, maringHighestValue, 0, drawHeight);

	forecastCtx.beginPath();
	forecastCtx.moveTo(xStart + statOptions.leftColumnWidth + statOptions.actualStickWidth / 2, (drawHeight - yStart) + statOptions.verticalOffset);
	forecastCtx.lineTo(xEnd + statOptions.leftColumnWidth + statOptions.actualStickWidth / 2, (drawHeight - yEnd) + statOptions.verticalOffset);
	forecastCtx.lineWidth = 1.5;
	forecastCtx.strokeStyle = 'rgba(46, 64, 87, 1)';
	forecastCtx.stroke();
}

function drawStatLine(statCanvas) {
	let statCtx = statCanvas.getContext("2d");
	statCtx.fillStyle = "lightgrey";
	let sectionWidth = (statCanvas.width - statOptions.leftColumnWidth - statOptions.rightSpacing) / statOptions.splitList.length;
	let highestValue = Math.max.apply(Math, statOptions.splitList.map(function(o) { return o[statOptions.valueRange]; }))
	let lowestValue = 0; //Math.min.apply(Math, splitList.map(function (o) {return o[valueRange];}))

	let drawHeight = statCanvas.height - statOptions.bottomStripHeight;

	let prevVal = statOptions.splitList[0][statOptions.valueRange];
	let prevXStart = undefined;
	let prevHeight = undefined;

	$(statOptions.splitList).each(function(index, element) {
		statCtx.fillStyle = 'rgb(46, 64, 87)';
		let xStart = index * sectionWidth;

		if (sectionWidth < statOptions.stickWidth)
			statOptions.actualStickWidth = statOptions.sectionWidth;

		xStart = Math.floor(xStart + ((sectionWidth - statOptions.actualStickWidth) / 2));

		if (prevVal == undefined)
			prevVal = element[statOptions.valueRange];

		let realValue = element[statOptions.valueRange];

		let creepingValue = realValue * statOptions.creepingAvg + prevVal * (1 - statOptions.creepingAvg);

		prevHeight = scale(prevVal, lowestValue, highestValue, 0, drawHeight);
		let creepingHeight = scale(creepingValue, lowestValue, highestValue, 0, drawHeight);

		if (prevXStart != undefined) {
			statCtx.beginPath();
			statCtx.moveTo(prevXStart + statOptions.leftColumnWidth + statOptions.actualStickWidth / 2, (drawHeight - prevHeight) + statOptions.verticalOffset);
			statCtx.lineTo(xStart + statOptions.leftColumnWidth + statOptions.actualStickWidth / 2, (drawHeight - creepingHeight) + statOptions.verticalOffset);
			statCtx.lineWidth = 1.5;
			statCtx.strokeStyle = 'rgba(46, 64, 87, 1)';
			statCtx.stroke();
		}

		prevVal = creepingValue;
		prevXStart = xStart;
	});
}

function scale(number, inMin, inMax, outMin, outMax) {
	return (number - inMin) * (outMax - outMin) / (inMax - inMin) + outMin;
}