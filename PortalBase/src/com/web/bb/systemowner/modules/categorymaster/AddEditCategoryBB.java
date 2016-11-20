package com.web.bb.systemowner.modules.categorymaster;

import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.component.tabview.Tab;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.TabChangeEvent;

import com.web.bf.systemowner.modules.categorymaster.CategoryMasterBF;
import com.web.common.constants.CommonConstant;
import com.web.common.dvo.opr.systemowner.CategoryOpr;
import com.web.common.dvo.systemowner.CategoryLevelDVO;
import com.web.common.dvo.util.File;
import com.web.common.dvo.util.OptionsDVO;
import com.web.common.jsf.converters.BaseDVOConverter;
import com.web.common.parents.BackingBean;
import com.web.foundation.exception.BusinessException;
import com.web.foundation.exception.FrameworkException;
import com.web.foundation.logger.ITSDLogger;
import com.web.foundation.logger.TSDLogger;
import com.web.foundation.validation.FoundationValidator;
import com.web.util.PropertiesReader;

public class AddEditCategoryBB extends BackingBean {

	private static final long serialVersionUID = -207056909000545895L;

	private String propertiesLocation = "/com/web/bb/systemowner/modules/categorymaster/category";
	private int activeTabIndex;

	private CategoryOpr addEditCategoryOpr;
	private transient BaseDVOConverter baseDVOConverter;

	public int getActiveTabIndex() {
		ITSDLogger myLog = TSDLogger.getLogger(this.getClass().getName());
		myLog.debug(" inside getActiveTabIndex: ");
		if (FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get(CommonConstant.ACTIVE_TAB) != null) {
			activeTabIndex = (Integer) FacesContext.getCurrentInstance().getExternalContext().getRequestMap()
					.get(CommonConstant.ACTIVE_TAB);
			FacesContext.getCurrentInstance().getExternalContext().getRequestMap().remove(CommonConstant.ACTIVE_TAB);
		}
		return activeTabIndex;
	}

	public void setActiveTabIndex(int activeTabIndex) {
		this.activeTabIndex = activeTabIndex;
	}

	@Override
	public void executeSearch(ActionEvent event) {
	}

	@Override
	public boolean validateSearch() {
		return false;
	}

	@Override
	public void executeSave(ActionEvent event) {
		ITSDLogger myLog = TSDLogger.getLogger(this.getClass().getName());
		myLog.debug(" executeSave starts: ");
		PropertiesReader propertiesReader = new PropertiesReader(propertiesLocation);

		boolean validate = validateSave();

		if (validate) {
			try {
				String userLogin = getUserLogin(FacesContext.getCurrentInstance().getExternalContext());
				addEditCategoryOpr.getCategoryRecord().setUserLogin(userLogin);
				addEditCategoryOpr = new CategoryMasterBF().executeSave(addEditCategoryOpr);
				setSuccessMsg(propertiesReader.getValueOfKey("category_save"));
			} catch (FrameworkException e) {
				handleException(e, propertiesLocation);
			} catch (BusinessException e) {
				handleException(e, propertiesLocation);
			}
		}

	}

	@Override
	public boolean validateSave() {
		ITSDLogger myLog = TSDLogger.getLogger(this.getClass().getName());
		myLog.debug("Inside validateSearch: ");

		boolean validate = true;

		FoundationValidator validator = new FoundationValidator();
		PropertiesReader propertiesReader = new PropertiesReader(propertiesLocation);

		setErrorList(new ArrayList<String>());
		setSuccessMsg("");

		boolean isCategoryName = validator.validateNull(addEditCategoryOpr.getCategoryRecord().getName());
		boolean isCategoryCode = validator.validateNull(addEditCategoryOpr.getCategoryRecord().getCode());

		if (!isCategoryCode) {
			addToErrorList(propertiesReader.getValueOfKey("category_code_null"));
			validate = false;
		}

		if (!isCategoryName) {
			addToErrorList(propertiesReader.getValueOfKey("category_name_null"));
			validate = false;
		}

		return validate;
	}

	@Override
	public void editDetails() {
	}

	@Override
	public void retrieveData() {
	}

	@Override
	public void executeAddRow(ActionEvent event) {
	}

	@Override
	public OptionsDVO getAllOptions() {
		return null;
	}

	@Override
	public void setAllOptions(OptionsDVO allOptions) {
	}

	public String setCategoryForEdit() {
		ITSDLogger myLog = TSDLogger.getLogger(this.getClass().getName());
		myLog.debug(" setCategoryForEdit starts: ");

		// if
		// (FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get(CommonConstant.ACTIVE_TAB)
		// != null) {
		// activeTabIndex = (Integer)
		// FacesContext.getCurrentInstance().getExternalContext().getRequestMap()
		// .get(CommonConstant.ACTIVE_TAB);
		// FacesContext.getCurrentInstance().getExternalContext().getRequestMap().remove(CommonConstant.ACTIVE_TAB);
		// }

		// CategoryOpr editCategoryOpr = (CategoryOpr)
		// FacesContext.getCurrentInstance().getExternalContext()
		// .getRequestMap().get(CommonConstant.ACTIVE_TAB_OPR);
		// myLog.debug("addEditCategoryOpr: " + addEditCategoryOpr);
		// myLog.debug("editCategoryOpr: " + editCategoryOpr);
		//
		// addEditCategoryOpr.setCategoryRecord(editCategoryOpr.getCategoryRecord());
		// myLog.debug(" Category Id: " +
		// editCategoryOpr.getCategoryRecord().getId());
		// myLog.debug(" Category Code: " +
		// editCategoryOpr.getCategoryRecord().getCode());
		// myLog.debug(" Category Name: " +
		// editCategoryOpr.getCategoryRecord().getName());

		myLog.debug("My name is "
				+ FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("MY_NAME"));

		return null;
	}

	public void resetPage() {
		ITSDLogger myLog = TSDLogger.getLogger(this.getClass().getName());
		myLog.debug(" resetPage starts: ");

	}

	public CategoryOpr getAddEditCategoryOpr() {
		ITSDLogger myLog = TSDLogger.getLogger(this.getClass().getName());
		myLog.debug(" inside getAddEditCategoryOpr: ");
		if (FacesContext.getCurrentInstance().getExternalContext().getRequestMap()
				.containsKey(CommonConstant.ACTIVE_TAB_OPR)) {
			CategoryOpr selectedAddEditCategoryOpr = (CategoryOpr) FacesContext.getCurrentInstance()
					.getExternalContext().getRequestMap().get(CommonConstant.ACTIVE_TAB_OPR);
			FacesContext.getCurrentInstance().getExternalContext().getRequestMap()
					.remove(CommonConstant.ACTIVE_TAB_OPR);
			addEditCategoryOpr.setCategoryRecord(selectedAddEditCategoryOpr.getSelectedCategoryRecord());
		}
		if (addEditCategoryOpr == null) {
			addEditCategoryOpr = new CategoryOpr();
		}
		return addEditCategoryOpr;
	}

	public void setAddEditCategoryOpr(CategoryOpr addEditCategoryOpr) {
		this.addEditCategoryOpr = addEditCategoryOpr;
	}

	public void handleFileUploadForCategoryImage(FileUploadEvent event) {
		ITSDLogger myLog = TSDLogger.getLogger(this.getClass().getName());
		myLog.debug(" Inside handleFileUploadForCategoryImage: ");

		String fileName = event.getFile().getFileName();
		myLog.debug("fileName " + fileName);
		String contextPath = "product/";

		PropertiesReader propertiesReader = new PropertiesReader(propertiesLocation);
		boolean uploadFlag = false;

		File uploadFile = extractUploadedFileData(event);

		String code = addEditCategoryOpr.getCategoryRecord().getCode();
		code = code.replace(" ", "_");
		String zoomFileName = code + "_0_z." + uploadFile.getExtension();
		myLog.debug("zoom fileName " + uploadFile.getName());
		uploadFlag = uploadImage(uploadFile, CommonConstant.ImageAttributes.ZOOM_IMAGE_WIDTH,
				CommonConstant.ImageAttributes.ZOOM_IMAGE_HEIGHT, contextPath);
		myLog.debug("uploadFlag zoom :: " + uploadFlag);

		if (uploadFlag) {

			// call upload for regular image
			String regularFileName = code + "_0_r." + uploadFile.getExtension();
			String regularFileUrl = contextPath + regularFileName;
			myLog.debug("regularFileUrl " + regularFileUrl);
			addEditCategoryOpr.getCategoryRecord().setImageUrl(regularFileUrl);
			uploadFile.setName(regularFileName);
			uploadFlag = uploadImage(uploadFile, CommonConstant.ImageAttributes.IMAGE_WIDTH,
					CommonConstant.ImageAttributes.IMAGE_HEIGHT, contextPath);
			myLog.debug("uploadFlag regular :: " + uploadFlag);
			// end call upload for regular image

			if (uploadFlag) {
				// call upload for thumbnail image
				String thumbFileName = code + "_0_t." + uploadFile.getExtension();
				String thumbFileUrl = contextPath + thumbFileName;
				myLog.debug("thumbFileUrl " + thumbFileUrl);
				uploadFile.setName(thumbFileName);
				uploadFlag = uploadImage(uploadFile, CommonConstant.ImageAttributes.THUMB_IMAGE_WIDTH,
						CommonConstant.ImageAttributes.THUMB_IMAGE_HEIGHT, contextPath);
				myLog.debug("uploadFlag thumbnail :: " + uploadFlag);
				// end call upload for thumbnail image

				if (uploadFlag) {
					// call upload for report image
					String reportFileName = code + "_0_rpt." + uploadFile.getExtension();
					String reportFileUrl = contextPath + reportFileName;
					myLog.debug("reportFileUrl " + reportFileUrl);
					uploadFile.setName(reportFileName);
					uploadFlag = uploadImage(uploadFile, CommonConstant.ImageAttributes.REPORT_IMAGE_WIDTH,
							CommonConstant.ImageAttributes.REPORT_IMAGE_HEIGHT, contextPath);
					myLog.debug("uploadFlag report :: " + uploadFlag);
					// end call upload for report image

					if (uploadFlag) {
						myLog.debug("All files uploaded successfully :::::::::: ");
						setSuccessMsg(propertiesReader.getValueOfKey("upload_successful"));
					} else {
						myLog.debug("ERROR uploading thumbnail :::::::::: ");
						addToErrorList(propertiesReader.getValueOfKey("upload_error"));
					}
				} else {
					myLog.debug("ERROR uploading thumbnail :::::::::: ");
					addToErrorList(propertiesReader.getValueOfKey("upload_error"));
				}
			} else {
				myLog.debug("ERROR uploading regular image :::::::::: ");
				addToErrorList(propertiesReader.getValueOfKey("upload_error"));
			}

		}
	}

	public void tabChanged(TabChangeEvent event) {
		ITSDLogger myLog = TSDLogger.getLogger(this.getClass().getName());
		myLog.debug(" In tabChanged : activeTabIndex : " + activeTabIndex);

		Tab activeTab = event.getTab();
		String tabId = activeTab.getId();
		myLog.debug(" tabChanged : tab id : " + tabId);

		if (tabId.equals("searchListCategory")) {
			FacesContext.getCurrentInstance().getExternalContext().getRequestMap()
					.put(CommonConstant.RE_INITIALIZE_OPR, new CategoryOpr());
			FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put(CommonConstant.ACTIVE_TAB, 0);
			// RequestContext.getCurrentInstance().execute("searchCustomerPurchaseBill();");

		} else if (tabId.equals("addeditCategory")) {
			FacesContext.getCurrentInstance().getExternalContext().getRequestMap()
					.put(CommonConstant.ACTIVE_TAB_OPR, new CategoryOpr());
			FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put(CommonConstant.ACTIVE_TAB, 1);
			// RequestContext.getCurrentInstance().execute("addEditCustomerPurchaseBill();");
		}
	}

	public void getMappedCategoryLevel(ActionEvent event) {
		ITSDLogger myLog = TSDLogger.getLogger(this.getClass().getName());
		myLog.debug(" Inside getMappedCategoryLevel : ");

		try {
			addEditCategoryOpr = new CategoryMasterBF().getMappedCategoryLevel(addEditCategoryOpr);
		} catch (FrameworkException e) {
			handleException(e, propertiesLocation);
		} catch (BusinessException e) {
			handleException(e, propertiesLocation);
		}
	}

	public List<CategoryLevelDVO> getSuggestedCategoryLevel(String query) {
		List<CategoryLevelDVO> newCategoryLevels = new ArrayList<CategoryLevelDVO>();
		if (query != null) {

			char[] queryCharacterArray = query.toCharArray();
			for (int i = 0; i < queryCharacterArray.length; i++) {
				if (!Character.isDigit(queryCharacterArray[i])) {
					return null;
				}
			}

			Integer inputLevel = Integer.parseInt(query);
			boolean isAlreadyMapped = false;
			boolean levelExceed = false;
			List<CategoryLevelDVO> categoryLevels = addEditCategoryOpr.getCategoryRecord().getCategoryLevels();

			for (CategoryLevelDVO categoryLevelRecord : categoryLevels) {

				Integer levelNo = categoryLevelRecord.getLevelNo();

				if (levelNo != null && levelNo.equals(inputLevel)) {
					isAlreadyMapped = true;
				}
			}
			if (inputLevel > 3) {
				levelExceed = true;
			}
			if (!levelExceed && !isAlreadyMapped) {
				CategoryLevelDVO categoryLevelRecord = new CategoryLevelDVO();
				categoryLevelRecord.setLevelNo(inputLevel);
				categoryLevelRecord.setCategoryRecord(addEditCategoryOpr.getCategoryRecord());
				newCategoryLevels.add(categoryLevelRecord);
			}
		}

		return newCategoryLevels;
	}

	public BaseDVOConverter getBaseDVOConverter() {
		if (baseDVOConverter == null) {
			baseDVOConverter = new BaseDVOConverter();
		}
		return baseDVOConverter;
	}

	public void setBaseDVOConverter(BaseDVOConverter baseDVOConverter) {
		this.baseDVOConverter = baseDVOConverter;
	}

}
