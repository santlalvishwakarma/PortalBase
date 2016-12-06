package com.web.bb.retail.module.home;

import javax.faces.event.ActionEvent;

import com.web.bf.retail.modules.home.HomeBF;
import com.web.common.constants.CommonConstant;
import com.web.common.dvo.opr.retail.HomeOpr;
import com.web.common.dvo.systemowner.HierarchyCategoryMappingDVO;
import com.web.common.dvo.util.OptionsDVO;
import com.web.common.parents.BackingBean;
import com.web.foundation.exception.BusinessException;
import com.web.foundation.exception.FrameworkException;

public class HomeBB extends BackingBean {

	private static final long serialVersionUID = -6290259264212631953L;
	private HomeOpr homeOpr;

	public HomeOpr getHomeOpr() {
		if (homeOpr == null) {
			homeOpr = new HomeOpr();
		}
		return homeOpr;
	}

	public void setHomeOpr(HomeOpr homeOpr) {
		this.homeOpr = homeOpr;
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
	}

	@Override
	public boolean validateSave() {
		return false;
	}

	@Override
	public void editDetails() {
	}

	@Override
	public void retrieveData() {
		getCategoryForHomePage();
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

	private void getCategoryForHomePage() {
		getHomeOpr().setHomePageCategoryList(null);
		try {
			homeOpr = new HomeBF().getCategoryForHomePage(homeOpr);

			if (!homeOpr.getHomePageCategoryList().isEmpty()) {

				for (HierarchyCategoryMappingDVO categoryMappingDVO : homeOpr.getHomePageCategoryList()) {
					StringBuffer categoryUrl = new StringBuffer();
					categoryUrl.append(CommonConstant.CONTEXT_PATH + CommonConstant.SLASH);
					categoryUrl.append(categoryMappingDVO.getHierarchyRecord().getCode());

					if (categoryMappingDVO.getCategoryLevelOneRecord().getCode() != null) {
						categoryUrl.append(CommonConstant.SLASH
								+ categoryMappingDVO.getCategoryLevelOneRecord().getCode());
					}

					if (categoryMappingDVO.getCategoryLevelTwoRecord().getCode() != null) {
						categoryUrl.append(CommonConstant.SLASH
								+ categoryMappingDVO.getCategoryLevelTwoRecord().getCode());
					}

					if (categoryMappingDVO.getCategoryLevelThreeRecord().getCode() != null) {
						categoryUrl.append(CommonConstant.SLASH
								+ categoryMappingDVO.getCategoryLevelThreeRecord().getCode());
					}

					if (categoryMappingDVO.getCategoryLevelFourRecord().getCode() != null) {
						categoryUrl.append(CommonConstant.SLASH
								+ categoryMappingDVO.getCategoryLevelFourRecord().getCode());
					}

					categoryUrl.append(CommonConstant.SLASH + "products" + CommonConstant.SLASH);

					categoryMappingDVO.setCategoryUrl(categoryUrl.toString());
				}
			}

		} catch (FrameworkException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}
}
