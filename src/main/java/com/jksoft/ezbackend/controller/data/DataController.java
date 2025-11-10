package com.jksoft.ezbackend.controller.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jksoft.ezbackend.config.security.user.User;
import com.jksoft.ezbackend.config.security.user.UserService;
import com.jksoft.ezbackend.entities.data.ItemWrapper;
import com.jksoft.ezbackend.entities.data.PropertyRecord;
import com.jksoft.ezbackend.entities.data.dto.WrapperDTO;
import com.jksoft.ezbackend.entities.structure.Company;
import com.jksoft.ezbackend.entities.structure.Item;
import com.jksoft.ezbackend.entities.structure.Property;
import com.jksoft.ezbackend.entities.structure.dto.ItemDTO;
import com.jksoft.ezbackend.service.ItemService;
import com.jksoft.ezbackend.service.data.RecordService;
import com.jksoft.ezbackend.service.data.WrapperService;
import com.jksoft.ezbackend.service.utils.DataUtils;

@Controller
@RequestMapping("data")
public class DataController {

	private UserService userService;
	private ItemService itemService;
	private WrapperService wrapperService;
	private RecordService recordService;
	private DataUtils dataUtils;

	public DataController(UserService userService, ItemService itemService, DataUtils dataUtils,
			WrapperService wrapperService, RecordService recordService) {
		this.userService = userService;
		this.itemService = itemService;
		this.dataUtils = dataUtils;
		this.wrapperService = wrapperService;
		this.recordService = recordService;
	}

	@GetMapping()
	public String getData2(Model model) {
		User user = userService.getCurrentUser();

		Company company = user.getCompany();
		model.addAttribute("company", company);
		return "data/overview";
	}

	@GetMapping("/namespace/{namespaceId}/item/{itemId}")
	public String getWrapperList(Model model, @PathVariable Long namespaceId, @PathVariable Long itemId) {
		Item item = this.itemService.readItem(itemId);
		model.addAttribute("item", item);
		
		ItemWrapper wrapper = new ItemWrapper();
		wrapper.setItem(item);
		
		List<PropertyRecord> recordList = new ArrayList<>();
		
		for (Property p : wrapper.getItem().getPropertyList()) {
			PropertyRecord record = new PropertyRecord();
			record.setProperty(p);
			record.setItemWrapper(wrapper);
			
			switch (p.getPropertyType()) {
				case BOOLEAN:
					break;
				case DECIMAL:
					break;
				case INTEGER:
					break;
				case EMBEDDED:
				case REFERENCE:
					Item referencedItem = p.getReference();
					List<ItemWrapper> referencedWrapperList = this.wrapperService.queryWrappers(referencedItem.getId());
					record.setReferenceList(referencedWrapperList);
					break;
				case SELECTION:
					break;
				case TEXT:
				default:
					record.setValue("");
					break;
			}
			recordList.add(record);
			
		}
		
		wrapper.setRecordList(recordList);
		
		model.addAttribute("wrapper", wrapper);
		
		List<ItemWrapper> wrapperList = wrapperService.queryWrappers(item);
		model.addAttribute("wrapperList", wrapperList);

		return "data/create";
	}

	@PostMapping("/namespace/{namespaceId}/item/{itemId}")
	public String createWrapper(Model model, @PathVariable Long namespaceId, @PathVariable Long itemId,
			@RequestParam Map<String, String> params) {
		Item item = this.itemService.readItem(itemId);

		WrapperDTO wrapper = this.dataUtils.getWrapperFromFormData(params);
		wrapper.setItem(ItemDTO.fromItem(item));
		
		wrapper.prettyPrint();
		
		ItemWrapper itemWrapper = wrapper.toItemWrapper();
		
		wrapperService.createWrapper(itemWrapper);

		return String.format("redirect:/data/namespace/%d/item/%d", namespaceId, itemId);
	}
	
	@GetMapping("/namespace/{namespaceId}/item/{itemId}/wrapper/{wrapperId}")
	public String getWrapper(Model model, @PathVariable Long namespaceId, @PathVariable Long itemId, @PathVariable Long wrapperId) {
		WrapperDTO wrapper = this.wrapperService.readWrapper(wrapperId);
		model.addAttribute("wrapper", wrapper);
		return "data/details";
	}
}
