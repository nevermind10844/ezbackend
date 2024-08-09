/**
 * Get the ISO week date week number
 */
Date.prototype.getWeek = function() {
	// Create a copy of this date object
	var target = new Date(this.valueOf());

	// ISO week date weeks start on Monday, so correct the day number
	var dayNr = (this.getDay() + 6) % 7;

	// ISO 8601 states that week 1 is the week with the first Thursday of that year
	// Set the target date to the Thursday in the target week
	target.setDate(target.getDate() - dayNr + 3);

	// Store the millisecond value of the target date
	var firstThursday = target.valueOf();

	// Set the target to the first Thursday of the year
	// First, set the target to January 1st
	target.setMonth(0, 1);

	// Not a Thursday? Correct the date to the next Thursday
	if (target.getDay() !== 4) {
		target.setMonth(0, 1 + ((4 - target.getDay()) + 7) % 7);
	}

	// The week number is the number of weeks between the first Thursday of the year
	// and the Thursday in the target week (604800000 = 7 * 24 * 3600 * 1000)
	return 1 + Math.ceil((firstThursday - target) / 604800000);
}

function setMutationCallback(id, callback){
	var observer = new MutationObserver(function (mutations) {
		callback(mutations);
	});
	var target = document.querySelector('#'+id);
	observer.observe(target, {
		attributes: true
	});
}