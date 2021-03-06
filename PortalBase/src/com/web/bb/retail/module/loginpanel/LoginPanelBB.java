package com.web.bb.retail.module.loginpanel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import com.ocpsoft.pretty.PrettyContext;
import com.web.bf.retail.modules.home.HomeBF;
import com.web.bf.retail.modules.loginpanel.LoginPanelBF;
import com.web.common.constants.CommonConstant;
import com.web.common.dvo.opr.retail.LoginPanelOpr;
import com.web.common.dvo.retail.modules.user.UserRoleMappingDVO;
import com.web.common.dvo.util.OptionsDVO;
import com.web.common.parents.BackingBean;
import com.web.foundation.exception.BusinessException;
import com.web.foundation.exception.FrameworkException;
import com.web.foundation.logger.ITSDLogger;
import com.web.foundation.logger.TSDLogger;
import com.web.foundation.validation.FoundationValidator;
import com.web.util.CommonUtil;
import com.web.util.PropertiesReader;

public class LoginPanelBB extends BackingBean {

	private static final long serialVersionUID = 7781795087882864778L;
	private String propertiesLocation = CommonConstant.MessageLocation.COMMON_MESSAGES;
	private String commonPropertiesLocation = CommonConstant.MessageLocation.COMMON_MESSAGES;
	private LoginPanelOpr loginPanelOpr;
	private LoginPanelOpr forgotPasswordOpr;
	private String displayName;
	private String retailLogin;
	private String wholesaleLogin;
	private String systemOwnerLogin;
	private String fromShoppingCartPage;
	private boolean navigationFlag = true;
	private boolean forgotPasswordSent;

	public boolean isNavigationFlag() {
		return navigationFlag;
	}

	public void setNavigationFlag(boolean navigationFlag) {
		this.navigationFlag = navigationFlag;
	}

	public String getFromShoppingCartPage() {
		return fromShoppingCartPage;
	}

	public void setFromShoppingCartPage(String fromShoppingCartPage) {
		this.fromShoppingCartPage = fromShoppingCartPage;
	}

	public String getRetailLogin() {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.put(CommonConstant.LOGIN_TYPE, CommonConstant.RETAIL_LOGIN_TYPE);
		return retailLogin;
	}

	public void setRetailLogin(String retailLogin) {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.put(CommonConstant.LOGIN_TYPE, CommonConstant.RETAIL_LOGIN_TYPE);
		this.retailLogin = retailLogin;
	}

	public String getWholesaleLogin() {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.put(CommonConstant.LOGIN_TYPE, CommonConstant.WHOLESALE_LOGIN_TYPE);
		return wholesaleLogin;
	}

	public void setWholesaleLogin(String wholesaleLogin) {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.put(CommonConstant.LOGIN_TYPE, CommonConstant.WHOLESALE_LOGIN_TYPE);
		this.wholesaleLogin = wholesaleLogin;
	}

	public String getSystemOwnerLogin() {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.put(CommonConstant.LOGIN_TYPE, CommonConstant.SYSTEMOWNER_LOGIN_TYPE);
		return systemOwnerLogin;
	}

	public void setSystemOwnerLogin(String systemOwnerLogin) {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.put(CommonConstant.LOGIN_TYPE, CommonConstant.SYSTEMOWNER_LOGIN_TYPE);
		this.systemOwnerLogin = systemOwnerLogin;
	}

	public void openLoginPopup(ActionEvent event) {
		getRetailLogin();
		loginPanelOpr = new LoginPanelOpr();
	}

	public void openAdminLoginPopup(ActionEvent event) {
		getSystemOwnerLogin();
		loginPanelOpr = new LoginPanelOpr();
	}

	@Override
	public OptionsDVO getAllOptions() {
		return null;
	}

	@Override
	public void setAllOptions(OptionsDVO allOptions) {

	}

	public void clearPage(ActionEvent event) {
		// TEMPLATE FOR CLEARPAGE METHOD ver 1.1
		FacesContext.getCurrentInstance().getExternalContext().getRequestMap()
				.put(CommonConstant.RE_INITIALIZE_OPR, CommonConstant.RE_INITIALIZE_OPR);
		setErrorList(new ArrayList<String>());
		setSuccessMsg("");
		loginPanelOpr = new LoginPanelOpr();
	}

	@Override
	public void editDetails() {

	}

	@Override
	public void executeSave(ActionEvent event) {

	}

	@Override
	public void executeSearch(ActionEvent event) {

	}

	@Override
	public boolean validateSave() {

		return false;
	}

	@Override
	public boolean validateSearch() {

		return false;
	}

	public LoginPanelOpr getLoginPanelOpr() {
		// ITSDLogger myLog = TSDLogger.getLogger(this.getClass().getName());
		if (loginPanelOpr == null
				|| FacesContext.getCurrentInstance().getExternalContext().getRequestMap()
						.containsKey(CommonConstant.RE_INITIALIZE_OPR)) {
			loginPanelOpr = new LoginPanelOpr();
		}

		return loginPanelOpr;

	}

	public void setLoginPanelOpr(LoginPanelOpr loginPanelOpr) {
		this.loginPanelOpr = loginPanelOpr;
	}

	public LoginPanelOpr getForgotPasswordOpr() {
		if (forgotPasswordOpr == null
				|| FacesContext.getCurrentInstance().getExternalContext().getRequestMap()
						.containsKey(CommonConstant.RE_INITIALIZE_OPR)) {
			forgotPasswordOpr = new LoginPanelOpr();
		}
		return forgotPasswordOpr;
	}

	public void setForgotPasswordOpr(LoginPanelOpr forgotPasswordOpr) {
		this.forgotPasswordOpr = forgotPasswordOpr;
	}

	private void doLogin() {
		ITSDLogger myLog = TSDLogger.getLogger(this.getClass().getName());
		setErrorList(new ArrayList<String>());
		setSuccessMsg("");
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		boolean validationFlag = validateLogin();
		PropertiesReader propertiesReader = new PropertiesReader();
		propertiesReader.setResourceBundle(propertiesLocation, Locale.getDefault());
		boolean invalidEmailPassCombinationFlag = false;

		if (validationFlag) {
			try {
				myLog.debug("login type:::" + externalContext.getSessionMap().get(CommonConstant.LOGIN_TYPE));
				if (externalContext.getSessionMap().get(CommonConstant.LOGIN_TYPE) != null) {

					if (externalContext.getSessionMap().get(CommonConstant.LOGIN_TYPE)
							.equals(CommonConstant.SYSTEMOWNER_LOGIN_TYPE)) {
						boolean adminValidationFlag = false;
						try {
							loginPanelOpr = new LoginPanelBF().executeLogin(loginPanelOpr);
						} catch (BusinessException e) {
							invalidEmailPassCombinationFlag = true;
							navigationFlag = false;
							String errorMessage = propertiesReader.getValueOfKey("invalid_user_msg");
							addToErrorList(errorMessage);
						}

						loginPanelOpr = new HomeBF().getUserBasedRole(loginPanelOpr);

						if (loginPanelOpr.getUserDetails().getUserRolesMappingList() != null
								&& !loginPanelOpr.getUserDetails().getUserRolesMappingList().isEmpty()
								&& !invalidEmailPassCombinationFlag) {
							for (UserRoleMappingDVO userRoleMappingDVO : loginPanelOpr.getUserDetails()
									.getUserRolesMappingList()) {
								if (CommonConstant.SYSTEM_OWNER.equals(userRoleMappingDVO.getRoleRecord().getCode())
										|| CommonConstant.ADMINISTRATOR.equals(userRoleMappingDVO.getRoleRecord()
												.getCode())) {
									navigationFlag = true;
									adminValidationFlag = true;
									String userLogin = loginPanelOpr.getUserDetails().getUserLogin();

									String serverlUrl = CommonUtil.getServerUrl();
									// putting user login into session
									externalContext.getSessionMap().put(CommonConstant.LOGGED_USER_KEY, userLogin);

									externalContext
											.getSessionMap()
											.put("imageURL",
													(CommonConstant.HttpSchemes.HTTP + serverlUrl + CommonConstant.Urls.WEBDAV_CONTEXT_NAME));

									putObjectInCache(CommonConstant.LOGGED_USER_ROLES, userRoleMappingDVO
											.getRoleRecord().getCode());

									// RequestContext.getCurrentInstance().execute("refreshLoginDetails();");

									setSuccessMsg(propertiesReader.getValueOfKey("user_logged_in_successfully_message"));
									break;
								}
							}

							if (!adminValidationFlag) {
								navigationFlag = false;
								String errorMessage = propertiesReader.getValueOfKey("not_authorized_admin");
								addToErrorList(errorMessage);
							} else {
								loginPanelOpr
										.getApplicationFlags()
										.getApplicationFlagMap()
										.put(CommonConstant.LOGIN_TYPE,
												externalContext.getSessionMap().get(CommonConstant.LOGIN_TYPE));
								externalContext.getSessionMap().put(CommonConstant.LOGGED_USER_KEY,
										loginPanelOpr.getUserDetails().getUserLogin());

								FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
										.put(CommonConstant.LOGGED_USER_DATA, loginPanelOpr.getUserDetails());
								putObjectInCache(CommonConstant.LOGGED_USER_DATA, loginPanelOpr.getUserDetails());
								FacesContext
										.getCurrentInstance()
										.getExternalContext()
										.getSessionMap()
										.put(CommonConstant.LOGGED_USER_NAME,
												loginPanelOpr.getUserDetails().getFirstName());
								RequestContext.getCurrentInstance().execute("refreshLoginDetails();");
							}
						} else if (!invalidEmailPassCombinationFlag) {
							navigationFlag = false;
							String errorMessage = propertiesReader.getValueOfKey("not_authorized_admin");
							addToErrorList(errorMessage);
						}

					} else {
						loginPanelOpr
								.getApplicationFlags()
								.getApplicationFlagMap()
								.put(CommonConstant.LOGIN_TYPE,
										externalContext.getSessionMap().get(CommonConstant.LOGIN_TYPE));
						loginPanelOpr = new LoginPanelBF().executeLogin(loginPanelOpr);
						externalContext.getSessionMap().put(CommonConstant.LOGGED_USER_KEY,
								loginPanelOpr.getUserDetails().getUserLogin());

						FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
								.put(CommonConstant.LOGGED_USER_DATA, loginPanelOpr.getUserDetails());
						putObjectInCache(CommonConstant.LOGGED_USER_DATA, loginPanelOpr.getUserDetails());
						FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
								.put(CommonConstant.LOGGED_USER_NAME, loginPanelOpr.getUserDetails().getFirstName());
						// RequestContext.getCurrentInstance().execute("refreshLoginDetails();");

						List<String> updateList = new ArrayList<String>();

						if (getUIComponent("shoppingCartForm") != null) {
							updateList.add("shoppingCartForm");
						}

						if (getUIComponent("browseProductsForm") != null) {
							updateList.add("browseProductsForm");
						}

						if (getUIComponent("readMoreForm") != null) {
							updateList.add("readMoreForm");
						}

						if (getUIComponent("homeForm") != null) {
							updateList.add("homeForm");
						}

						if (getUIComponent("paymentForm") != null) {
							updateList.add("paymentForm");
						}

						RequestContext.getCurrentInstance().update(updateList);
					}
				} else {
					String errorMessage = propertiesReader.getValueOfKey("system_error_login_type_null");
					addToErrorList(errorMessage);
					navigationFlag = false;
				}
			} catch (FrameworkException e) {
				// handle framework exception
				handleException((Exception) e, propertiesLocation);
				navigationFlag = false;
			} catch (BusinessException e) {
				// handle BusinessException;
				handleException((Exception) e, propertiesLocation);
				navigationFlag = false;
			}

		}

	}

	private UIComponent getUIComponent(String formId) {

		return FacesContext.getCurrentInstance().getViewRoot().findComponent(formId);
	}

	public void executeLogin(ActionEvent ae) {
		doLogin();
	}

	public String navigateAfterLogin() throws FrameworkException, BusinessException {
		doLogin();
		ITSDLogger myLog = TSDLogger.getLogger(this.getClass().getName());
		String websiteURL = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest())
				.getServerName();
		String requestUrl = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest())
				.getContextPath() + PrettyContext.getCurrentInstance().getRequestURL();
		myLog.debug("websiteURL " + websiteURL + "AND" + requestUrl);
		websiteURL = "http://" + websiteURL + requestUrl;
		String returnNavigationString = null;
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		if (navigationFlag) {
			// navigate to appropriate home page
			if (externalContext.getSessionMap().get(CommonConstant.LOGIN_TYPE) != null) {
				if (CommonConstant.RETAIL_LOGIN_TYPE.equals(externalContext.getSessionMap().get(
						CommonConstant.LOGIN_TYPE))) {
					// returnNavigationString = "pretty:homePage";
					try {
						externalContext.redirect(websiteURL);
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else if (CommonConstant.SYSTEMOWNER_LOGIN_TYPE.equals(externalContext.getSessionMap().get(
						CommonConstant.LOGIN_TYPE))) {
					returnNavigationString = "pretty:adminHome";
				}
			}
		}
		return returnNavigationString;
	}

	public String logout() {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();

		String returnNavigationString = null;
		if (externalContext.getSessionMap().get(CommonConstant.LOGIN_TYPE) != null) {
			if (CommonConstant.RETAIL_LOGIN_TYPE.equals(externalContext.getSessionMap().get(CommonConstant.LOGIN_TYPE))) {
				returnNavigationString = "pretty:homePage";
			}
			// else if
			// (CommonConstant.WHOLESALE_LOGIN_TYPE.equals(externalContext.getSessionMap().get(
			// CommonConstant.LOGIN_TYPE))) {
			// returnNavigationString = CommonConstant.WHOLESALE_HOME_PAGE;
			// }
			else if (CommonConstant.SYSTEMOWNER_LOGIN_TYPE.equals(externalContext.getSessionMap().get(
					CommonConstant.LOGIN_TYPE))) {
				returnNavigationString = "pretty:adminHome";
			}
		}

		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}

		return returnNavigationString;
	}

	public void performLogin(ActionEvent event) {
		ITSDLogger myLog = TSDLogger.getLogger(this.getClass().getName());
		myLog.debug(" inside performLogin ::: ");

		if (validateLogin()) {
			try {
				loginPanelOpr = new HomeBF().performLogin(loginPanelOpr);
				loginPanelOpr = new HomeBF().getUserBasedRole(loginPanelOpr);

				putObjectInCache(CommonConstant.LOGGED_USER_KEY, loginPanelOpr.getUserDetails().getUserLogin());

				putObjectInCache(CommonConstant.LOGGED_USER_DATA, loginPanelOpr.getUserDetails());

				String userName = "";
				if (loginPanelOpr.getUserDetails().getFirstName() != null) {
					userName += loginPanelOpr.getUserDetails().getFirstName();
				}
				if (loginPanelOpr.getUserDetails().getLastName() != null) {
					userName += " " + loginPanelOpr.getUserDetails().getLastName();
				}
				putObjectInCache(CommonConstant.LOGGED_USER_NAME, userName);

				for (int i = 0; i < loginPanelOpr.getUserDetails().getUserRolesMappingList().size();) {
					putObjectInCache(CommonConstant.LOGGED_USER_ROLES, loginPanelOpr.getUserDetails()
							.getUserRolesMappingList().get(i).getRoleRecord().getCode());
					break;
				}

				ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

				String websiteUrl = ((HttpServletRequest) externalContext.getRequest()).getServerName();

				String requestPage = "p" + PrettyContext.getCurrentInstance().getRequestURL();

				websiteUrl = "http://" + websiteUrl + ":8080/" + requestPage;
				myLog.debug(" websiteUrl ::: " + websiteUrl);
				externalContext.redirect(websiteUrl);

			} catch (FrameworkException e) {
				handleException(e, commonPropertiesLocation);
			} catch (BusinessException e) {
				handleException(e, commonPropertiesLocation);
			} catch (IOException e) {
				myLog.error(" error message occured during redirect ::: " + e.getMessage());
				e.printStackTrace();
			}
		}
	}

	private boolean validateLogin() {
		ITSDLogger myLog = TSDLogger.getLogger(this.getClass().getName());
		myLog.debug(" inside validateLogin ::: ");

		FoundationValidator validator = new FoundationValidator();
		PropertiesReader propertiesReader = new PropertiesReader(propertiesLocation);

		boolean validateFlag = true;

		String userLogin = loginPanelOpr.getUserDetails().getUserLogin();
		String password = loginPanelOpr.getUserDetails().getLoginPassword();

		if (!validator.validateNull(userLogin)) {
			addToErrorList(propertiesReader.getValueOfKey("login_null"));
		}

		if (!validator.validateNull(password)) {
			addToErrorList(propertiesReader.getValueOfKey("login_password_null"));
		}

		if (getErrorList().size() > 0) {
			validateFlag = false;
		}

		return validateFlag;
	}

	public void executeForgotPassword(ActionEvent event) {
		// ITSDLogger myLog = TSDLogger.getLogger(this.getClass().getName());
		setErrorList(new ArrayList<String>());
		setSuccessMsg("");
		PropertiesReader propertiesReader = new PropertiesReader();
		propertiesReader.setResourceBundle(propertiesLocation, Locale.getDefault());
		FoundationValidator validator = new FoundationValidator();
		boolean validationFlag = false;

		if (!validator.validateNull(forgotPasswordOpr.getUserDetails().getUserLogin())) {
			String errorMessage = propertiesReader.getValueOfKey("enter_email");
			addToErrorList(errorMessage);
		} else if (!validator.validateEmail(forgotPasswordOpr.getUserDetails().getUserLogin())) {
			addToErrorList(propertiesReader.getValueOfKey("email_invalid"));
		}

		if (getErrorList().size() > 0) {
			validationFlag = false;
		} else {
			validationFlag = true;
		}

		String domain = FacesContext.getCurrentInstance().getExternalContext().getRequestServerName();
		// myLog.debug("check for domain:::::::" + domain);
		forgotPasswordOpr.getApplicationFlags().getApplicationFlagMap().put(CommonConstant.WEBSITE_URL, domain);

		if (validationFlag) {
			try {
				forgotPasswordOpr = new LoginPanelBF().executeForgotPassword(forgotPasswordOpr);
				forgotPasswordSent = true;
				RequestContext.getCurrentInstance().execute("forgotPasswordPopupClose();");
			} catch (FrameworkException e) {
				// handle framework exception
				handleException((Exception) e, propertiesLocation);
			} catch (BusinessException e) {
				// handle BusinessException;
				handleException((Exception) e, propertiesLocation);
			}
		}
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public boolean isForgotPasswordSent() {
		return forgotPasswordSent;
	}

	public void setForgotPasswordSent(boolean forgotPasswordSent) {
		this.forgotPasswordSent = forgotPasswordSent;
	}

	@Override
	public void retrieveData() {
	}

	@Override
	public void executeAddRow(ActionEvent event) {
	}

	public void executeLogout(ActionEvent event) {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

		removeUserCache((String) externalContext.getSessionMap().get(CommonConstant.LOGGED_USER_KEY));
		externalContext.getSessionMap().remove(CommonConstant.LOGGED_USER_KEY);
		externalContext.getSessionMap().remove(CommonConstant.LOGGED_USER_NAME);
		removeObjectFromCache(CommonConstant.LOGGED_USER_DATA);

		try {
			HttpSession httpSession = (HttpSession) externalContext.getSession(false);
			httpSession.invalidate();
			httpSession = null;

			PropertiesReader propertiesReader = new PropertiesReader(commonPropertiesLocation);
			setSuccessMsg(propertiesReader.getValueOfKey("logout_success"));

			List<String> updateList = new ArrayList<String>();

			if (getUIComponent("shoppingCartForm") != null) {
				updateList.add("shoppingCartForm");
			}

			if (getUIComponent("browseProductsForm") != null) {
				updateList.add("browseProductsForm");
			}

			if (getUIComponent("readMoreForm") != null) {
				updateList.add("readMoreForm");
			}

			if (getUIComponent("homeForm") != null) {
				updateList.add("homeForm");
			}

			if (getUIComponent("paymentForm") != null) {
				updateList.add("paymentForm");
			}

			RequestContext.getCurrentInstance().update(updateList);
			;

		} catch (Exception e) {
			handleException(e, "Logout was not successfull.");
		}
	}

}
