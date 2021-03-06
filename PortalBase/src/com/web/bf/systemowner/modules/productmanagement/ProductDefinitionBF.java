package com.web.bf.systemowner.modules.productmanagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.web.bc.common.OptionsHelperBC;
import com.web.bc.systemowner.modules.productmanagement.ProductDefinitionBC;
import com.web.common.constants.CommonConstant;
import com.web.common.dvo.common.Parameter;
import com.web.common.dvo.opr.common.ParameterOpr;
import com.web.common.dvo.opr.systemowner.ProductOpr;
import com.web.common.dvo.systemowner.CategoryDVO;
import com.web.common.dvo.systemowner.HierarchyCategoryMappingDVO;
import com.web.common.dvo.systemowner.HierarchyDVO;
import com.web.common.dvo.systemowner.ProductDVO;
import com.web.common.dvo.systemowner.ProductSkuDVO;
import com.web.common.dvo.systemowner.PropertyDVO;
import com.web.common.parents.BusinessFacade;
import com.web.foundation.exception.BusinessException;
import com.web.foundation.exception.FrameworkException;
import com.web.sf.modules.core.CoreSF;

/**
 * @author NIRAJ
 * 
 */
public class ProductDefinitionBF extends BusinessFacade {

	public ArrayList<Object> getSuggestedHierarchies(HierarchyDVO productHierarchyDVO) throws FrameworkException,
			BusinessException {
		return new CoreSF().getSuggestedHierarchies(productHierarchyDVO);
	}

	public List<PropertyDVO> getSuggestedPropertiesBasedOnName(String propertyName) throws FrameworkException,
			BusinessException {
		return new CoreSF().getSuggestedPropertiesBasedOnName(propertyName);
	}

	public ParameterOpr getOptionsOnParameterCode(ParameterOpr parameterOpr) throws FrameworkException,
			BusinessException {
		OptionsHelperBC optionsHelperBC = new OptionsHelperBC();
		return optionsHelperBC.getOptionsOnParameterCode(parameterOpr);
	}

	public ProductOpr executeSearch(ProductOpr productOpr) throws FrameworkException, BusinessException {
		return new ProductDefinitionBC().executeSearch(productOpr);
	}

	public ProductOpr executeSaveProductSKUDetails(ProductOpr productOpr) throws FrameworkException, BusinessException {
		return new ProductDefinitionBC().executeSaveProductDetails(productOpr);
	}

	public ProductOpr getProductDetails(ProductOpr productOpr) throws FrameworkException, BusinessException {
		return new ProductDefinitionBC().getProductDetails(productOpr);
	}

	public ArrayList<Object> getSuggestedProductsList(ProductDVO productDVO) throws FrameworkException,
			BusinessException {
		return new ProductDefinitionBC().getSuggestedProductsList(productDVO);
	}

	public ProductOpr getHierarchiesMappingList(ProductOpr productOpr) throws FrameworkException, BusinessException {
		return new ProductDefinitionBC().getHierarchiesMappingList(productOpr);
	}

	public ProductOpr saveHierarchiesMappingList(ProductOpr productOpr) throws FrameworkException, BusinessException {
		return new ProductDefinitionBC().saveHierarchiesMappingList(productOpr);
	}

	public ProductOpr getPropertiesMappingList(ProductOpr productOpr) throws FrameworkException, BusinessException {
		return new ProductDefinitionBC().getPropertiesMappingList(productOpr);
	}

	public ProductOpr savePropertiesMappingList(ProductOpr productOpr) throws FrameworkException, BusinessException {
		return new ProductDefinitionBC().savePropertiesMappingList(productOpr);
	}

	public ProductOpr getRawMaterialMappingList(ProductOpr productOpr) throws FrameworkException, BusinessException {
		return new ProductDefinitionBC().getRawMaterialMappingList(productOpr);
	}

	public ProductOpr saveRawMaterialMappingList(ProductOpr productOpr) throws FrameworkException, BusinessException {
		return new ProductDefinitionBC().saveRawMaterialMappingList(productOpr);
	}

	public ProductOpr getProcessVariationMappingList(ProductOpr productOpr) throws FrameworkException,
			BusinessException {
		return new ProductDefinitionBC().getProcessVariationMappingList(productOpr);
	}

	public ProductOpr saveProcessVariationMappingList(ProductOpr productOpr) throws FrameworkException,
			BusinessException {
		return new ProductDefinitionBC().saveProcessVariationMappingList(productOpr);
	}

	public ProductOpr getComplementarySkuMappingList(ProductOpr productOpr) throws FrameworkException,
			BusinessException {
		return new ProductDefinitionBC().getComplementarySkuMappingList(productOpr);
	}

	public ProductOpr saveComplementarySkuMappingList(ProductOpr productOpr) throws FrameworkException,
			BusinessException {
		return new ProductDefinitionBC().saveComplementarySkuMappingList(productOpr);
	}

	public ProductOpr getSimilarSkuMappingList(ProductOpr productOpr) throws FrameworkException, BusinessException {
		return new ProductDefinitionBC().getSimilarSkuMappingList(productOpr);
	}

	public ProductOpr saveSimilarSkuMappingList(ProductOpr productOpr) throws FrameworkException, BusinessException {
		return new ProductDefinitionBC().saveSimilarSkuMappingList(productOpr);
	}

	public ProductOpr getPropsMappingList(ProductOpr productOpr) throws FrameworkException, BusinessException {
		return new ProductDefinitionBC().getPropsMappingList(productOpr);
	}

	public ProductOpr executeSavePropsMapping(ProductOpr productOpr) throws FrameworkException, BusinessException {
		return new ProductDefinitionBC().executeSavePropsMapping(productOpr);
	}

	public ProductOpr getCatalogMappingList(ProductOpr productOpr) throws FrameworkException, BusinessException {
		return new ProductDefinitionBC().getCatalogMappingList(productOpr);
	}

	public ProductOpr executeSaveCatalogMapping(ProductOpr productOpr) throws FrameworkException, BusinessException {
		return new ProductDefinitionBC().executeSaveCatalogMapping(productOpr);
	}

	public ProductOpr executeCopyProductSKU(ProductOpr productOpr) throws FrameworkException, BusinessException {
		return new ProductDefinitionBC().executeCopyProductSKU(productOpr);
	}

	public ProductOpr executeApproveProductSku(ProductOpr productOpr) throws FrameworkException, BusinessException {
		return new ProductDefinitionBC().executeApproveProductSku(productOpr);
	}

	public ProductOpr executeDeactivateProductSku(ProductOpr productOpr) throws FrameworkException, BusinessException {
		return new ProductDefinitionBC().executeDeactivateProductSku(productOpr);
	}

	public ProductOpr modifyProductSKU(ProductOpr productOpr) throws FrameworkException, BusinessException {
		return new ProductDefinitionBC().modifyProductSKU(productOpr);
	}

	public ArrayList<Object> getStatusCodeList() throws FrameworkException, BusinessException {
		return new OptionsHelperBC().getStatusCodeListBasedOnParameter(new Parameter(
				CommonConstant.ParameterCode.ACTIVE_INACTIVE_STATUS, null));
	}

	public List<CategoryDVO> getAllCategories() throws FrameworkException, BusinessException {
		return new CoreSF().getAllCategories();
	}

	public HashMap<String, ArrayList<Object>> getAllOptionsValuesForProduct() throws FrameworkException,
			BusinessException {

		HashMap<String, ArrayList<Object>> allOptionsValues = new HashMap<String, ArrayList<Object>>();
		OptionsHelperBC optionsHelperBC = new OptionsHelperBC();

		ParameterOpr parameterOpr = new ParameterOpr();

		parameterOpr = optionsHelperBC.getOptionsOnParameterCode(parameterOpr);

		return allOptionsValues;
	}

	public HashMap<String, ArrayList<Object>> getAllOptionsValuesForSearch() throws FrameworkException,
			BusinessException {
		HashMap<String, ArrayList<Object>> allOptionsValues = new HashMap<String, ArrayList<Object>>();
		ParameterOpr parameterOpr = new ParameterOpr();

		parameterOpr.getParameterList().add(
				new Parameter(CommonConstant.ParameterCode.PRODUCT_PROPERTIES_CONDITIONS, null));

		parameterOpr = new OptionsHelperBC().getOptionsOnParameterCode(parameterOpr);
		allOptionsValues.put("conditionList",
				parameterOpr.getParameterOptionsMap().get(CommonConstant.ParameterCode.PRODUCT_PROPERTIES_CONDITIONS));

		allOptionsValues.put("yesNoList", new OptionsHelperBC().getYesNoList());

		return allOptionsValues;
	}

	public ArrayList<ProductSkuDVO> searchProductSkuDetails(ProductDVO productRecord) throws FrameworkException,
			BusinessException {
		return new ProductDefinitionBC().searchProductSkuDetails(productRecord);
	}

	public ProductOpr getOtherSkuMappingList(ProductOpr productOpr) throws FrameworkException, BusinessException {
		return new ProductDefinitionBC().getOtherSkuMappingMappingDetails(productOpr);
	}

	public ArrayList<Object> getSuggestedOtherSKUList(ProductSkuDVO productSkuDVO) throws FrameworkException,
			BusinessException {
		return new ProductDefinitionBC().getSuggestedOtherSKUList(productSkuDVO);
	}

	public ProductOpr executeSaveOtherSkuMapping(ProductOpr productOpr) throws FrameworkException, BusinessException {
		return new ProductDefinitionBC().executeSaveOtherSkuMapping(productOpr);
	}

	public ProductOpr saveBOMCaptureDetailsForProduct(ProductOpr productOpr) throws FrameworkException,
			BusinessException {
		return new ProductDefinitionBC().saveBOMCaptureDetailsForProduct(productOpr);
	}

	public ProductOpr getProductSkuBomDetailsList(ProductOpr productOpr) throws FrameworkException, BusinessException {
		return new ProductDefinitionBC().getProductSkuBomDetailsList(productOpr);
	}

	public ProductOpr getSkuBomProcessVariationDetails(ProductOpr productOpr) throws FrameworkException,
			BusinessException {
		return new ProductDefinitionBC().getSkuBomProcessVariationDetails(productOpr);
	}

	public ProductDVO getProductSpecificDetails(ProductDVO productDVO) throws FrameworkException, BusinessException {
		return new ProductDefinitionBC().getProductSpecificDetails(productDVO);
	}

	public ProductDVO getProductSkuPropertySpecificDetails(ProductDVO productDVO) throws FrameworkException,
			BusinessException {
		return new ProductDefinitionBC().getProductSkuPropertySpecificDetails(productDVO);
	}

	public ProductOpr getProductsInCatalog(ProductOpr queryProductOpr) throws FrameworkException, BusinessException {
		ProductOpr returnProductOpr = new ProductOpr();
		returnProductOpr = new ProductDefinitionBC().getProductsInCatalog(queryProductOpr);
		return returnProductOpr;
	}

	public List<Object> getSuggestedCategoriesBasedOnCategoryAndLevel(
			HierarchyCategoryMappingDVO hierarchyCategoryMappingDVO) throws FrameworkException, BusinessException {
		return new ProductDefinitionBC().getSuggestedCategoriesBasedOnCategoryAndLevel(hierarchyCategoryMappingDVO);
	}

	public ProductOpr getProductHierarchyCategoryMappingList(ProductOpr productOpr) throws FrameworkException,
			BusinessException {
		return new ProductDefinitionBC().getProductHierarchyCategoryMappingList(productOpr);
	}

}
