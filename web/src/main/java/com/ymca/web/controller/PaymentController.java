package com.ymca.web.controller;

//import java.security.Principal;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
/*import jxl.Workbook;
 import jxl.WorkbookSettings;
 import jxl.write.Label;
 import jxl.write.NumberFormat;
 import jxl.write.WritableCellFormat;
 import jxl.write.WritableFont;
 import jxl.write.WritableSheet;
 import jxl.write.WritableWorkbook;
 */
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import com.ymca.dao.AccountContactDao;
import com.ymca.dao.AccountDao;
import com.ymca.dao.InvoiceDao;
import com.ymca.dao.JetPayPaymentDao;
import com.ymca.dao.PayerDao;
import com.ymca.dao.PaymentDao;
import com.ymca.dao.PaymentMethodDao;
import com.ymca.dao.SignupDao;
import com.ymca.dao.SystemPropertyDao;
import com.ymca.model.Account;
import com.ymca.model.AccountContact;
import com.ymca.model.Invoice;
import com.ymca.model.JetPayPayment;
import com.ymca.model.Payment;
import com.ymca.model.PaymentMethod;
import com.ymca.model.Signup;
import com.ymca.model.SystemProperty;
import com.ymca.model.User;
import com.ymca.web.enums.ACH_AccountTypeEnum;
import com.ymca.web.enums.JetpayTransactionTypeEnum;
import com.ymca.web.enums.PaymentMethodTypeEnum;
import com.ymca.web.enums.PaymentTypeEnum;
import com.ymca.web.enums.PortalStatusEnum;
import com.ymca.web.util.Constants;

@SuppressWarnings("deprecation")
@Controller
public class PaymentController extends BaseController {

	@Resource
	private AccountDao accountDao;

	@Resource
	private PaymentMethodDao paymentMethodDao;

	@Resource
	private JetPayPaymentDao jetPayPaymentDao;

	@Resource
	private PaymentDao paymentDao;

	@Resource
	private SystemPropertyDao systemPropertyDao;

	@Resource
	private InvoiceDao invoiceDao;

	@Resource
	private SignupDao signupDao;

	@Resource
	private PayerDao payerDao;
	
	@Resource
	private AccountContactDao accountContactDao;

	@RequestMapping(value = "/viewPaymentHistory", method = RequestMethod.GET)
	public String viewPaymentHistory(final HttpServletRequest request,
			final HttpServletResponse response) {
		return "viewPaymentHistory";
	}

	@RequestMapping(value = "/viewPaymentInformation", method = RequestMethod.GET)
	public ModelAndView viewPaymentInformation(
			final HttpServletRequest request, final HttpServletResponse response) {
		Model model = new ExtendedModelMap();
		try {

			Authentication a = SecurityContextHolder.getContext()
					.getAuthentication();
			String userId = null;
			try {
				userId = ((UserDetails) a.getPrincipal()).getUsername();
			} catch (Exception e) {
				model.addAttribute("errMsg", "Your session is expired");
				return new ModelAndView("viewPaymentInformation", model.asMap());
			}

			Account account = null;
			User user = null;
			List<PaymentMethod> paymentMethodList = null;
			List<SystemProperty> systemPropertyLst = null;
			if (userId != null && !"".equals(userId)) {
				account = accountDao.getByEmail(userId);
				request.setAttribute("userId", userId);
				user = getUserByAccount(account, user);
				paymentMethodList = paymentMethodDao
						.getPaymentMethodListForAccountID(account
								.getAccountId());
				// paymentMethodList = account.getPaymentMethod();
				systemPropertyLst = systemPropertyDao.getByPicklistName(Constants.PickListName_PAYMENT_METHOD);
			}
			// PHistory start
			Date startDate = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(startDate);
			cal.add(Calendar.DAY_OF_YEAR, -3);
			// Date formattedStartDate = cal.getTime();

			Date endDate = new Date();
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(endDate);
			cal1.add(Calendar.DAY_OF_YEAR, 3);
			// Date formattedEndDate = cal1.getTime();

			/*
			 * List<Payment> payments = new ArrayList<>(); List<Object>
			 * paymentLst = null; //paymentLst =
			 * paymentDao.getPaymentHistory("Credit"
			 * ,"Failure","Kate","winslet",4L,
			 * formattedStartDate,formattedEndDate); if(userId != null &&
			 * !"".equals(userId)){ account = accountDao.getByEmail(userId);
			 * request.setAttribute("userId", userId); user =
			 * getUserByAccount(account, user); paymentList =
			 * account.getPaymentMethod(); //paymentLst
			 * =paymentDao.getPaymentHistory
			 * ("Credit","Failure","kwinslet",account.getAccountId(),
			 * formattedStartDate,formattedEndDate); for(Object payment1 :
			 * paymentLst){ Object objArr1[] = (Object[]) payment1; //
			 * objArr1[1]=String.format("%.2f",objArr1[1]); //
			 * objArr1[3]=objArr1[3].toString().substring(0, 10);
			 * if(objArr1[4].toString().equalsIgnoreCase("Failure")){
			 * objArr1[4]=(objArr1[4]+"("+objArr1[5]+")"); }else{
			 * objArr1[4]=objArr1[4]; } Payment payment= new Payment(); Signup
			 * signup = new Signup(); Product item = new Product();
			 * signup.setItem(item); payment.setSignup(signup);
			 * item.setCategory(objArr1[0].toString());
			 * //payment.getSignup().getItem
			 * ().setCategory(objArr1[0].toString());
			 * payment.setCategory(objArr1[0].toString());
			 * payment.setAmount(Double.valueOf(objArr1[1].toString()));
			 * payment.setType(objArr1[2].toString()); DateFormat formatter =
			 * new SimpleDateFormat("yyyy-MM-dd"); Date pDate = null; try {
			 * pDate = formatter.parse(objArr1[3].toString()); } catch
			 * (ParseException e) { log.error("Error  ",e); }
			 * payment.setPaymentDate(pDate);
			 * payment.setStatus(objArr1[4].toString());
			 * payment.setReason(objArr1[5].toString()); payments.add(payment);
			 * } }
			 */

			/*
			 * //System.out.println("Size:"+payments.size()); for(Payment
			 * payment2 : payments) { //System.out.println(
			 * "-----------------------------------------------------------------------"
			 * );
			 * //System.out.println("Program:"+payment2.getCategory()+"  Amount:"
			 * +
			 * payment2.getAmount()+"  Type:"+payment2.getType()+"  Date:"+payment2
			 * .getPaymentDate()+"  Status:"+payment2.getStatus()+"  Reason:"+
			 * payment2.getReason()); }
			 */
			// Phistory end

			if (account != null) {
				model.addAttribute("accInfo", account);
				model.addAttribute("usInfo", user);
				int userCount = account.getUser().size();
				/*List<User> userS = new ArrayList<User>();
				int countmembers = 0;
				if (userCount > 1) {
					for (User u : account.getUser()) {
						if (user.getPartyId() != u.getPartyId() && u.isActive()) {
							countmembers = countmembers + 1;
							userS.add(u);
						}
					}
				}*/
				List<User> userS = new ArrayList<User>();	        
				int countmembers = 0;		        
				List<AccountContact> accountContactLst =  accountContactDao.getByCustomer(account);
				for(AccountContact ac : accountContactLst){
					if(user.getPartyId() != ac.getContact().getPartyId() && ac.getContact().isActive() && ac.getEndDate() == null){
						countmembers = countmembers + 1;
						userS.add(ac.getContact());
					}				
				}
				List<SystemProperty> yTaxID = new ArrayList<SystemProperty>();
				 yTaxID = systemPropertyDao.getPropertyByKeyName(Constants.Y_TAX_ID);
				 if(yTaxID != null && !yTaxID.isEmpty()) {
					 model.addAttribute("yTaxID", yTaxID.get(0).getKeyValue());
				 }
				String contextPath = request.getContextPath();
				model.addAttribute("contextPath", contextPath);
				model.addAttribute("userCount", countmembers);
				model.addAttribute("userS", userS);
				model.addAttribute("paymentInfoLst", paymentMethodList);
				// model.addAttribute("paymentList", payments);
				model.addAttribute("systemPropertyLst", systemPropertyLst);
				model.addAttribute("AccountID", account.getAccountId());
				
				 
			} else {
				model.addAttribute("errMsg", "Please Login");
				return new ModelAndView("login", model.asMap());
			}
		} catch (Exception e) {
			log.error("Error  ", e);
			model.addAttribute("errMsg", "Please Login");
			return new ModelAndView("login", model.asMap());
		}

		return new ModelAndView("viewPaymentInformation", model.asMap());
	}

	@RequestMapping(value = "getPaymentHistoryBySearchCriteria", method = RequestMethod.GET)
	public @ResponseBody
	String getPaymentHistoryBySearchCriteria(
			@RequestParam(value = "pType") String pType,
			@RequestParam(value = "pStatus") String pStatus,
			@RequestParam(value = "username") String username,
			@RequestParam(value = "stDate") String stDate,
			@RequestParam(value = "eDate") String eDate,
			final HttpServletRequest request, final HttpServletResponse response) {

		try {
			List<Object> paymentLst = new ArrayList<>();
			JSONArray json = new JSONArray();
			Authentication a = SecurityContextHolder.getContext()
					.getAuthentication();
			String userId = null;
			userId = ((UserDetails) a.getPrincipal()).getUsername();
			Account account = null;

			SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
			Date startDate = format.parse(stDate);
			Date endDate = format.parse(eDate);
			if (userId != null && !"".equals(userId)) {
				account = accountDao.getByEmail(userId);

				if (StringUtils.isNotBlank(username)) {
					paymentLst = paymentDao.getPaymentHistoryByContact(
							username, account.getAccountId(), startDate,
							endDate);
				} else if (pType.equalsIgnoreCase("ALL")
						&& pStatus.equalsIgnoreCase("ALL")) {
					paymentLst = paymentDao.getPaymentHistoryByName(
							account.getAccountId(), startDate, endDate);
				} else if (pType.equalsIgnoreCase("ALL")) {
					paymentLst = paymentDao
							.getPaymentHistoryByNameandstatus(pStatus,
									account.getAccountId(), startDate, endDate);
				} else if (pStatus.equalsIgnoreCase("ALL")) {
					paymentLst = paymentDao.getPaymentHistoryByNameandtype(
							pType, account.getAccountId(), startDate, endDate);
					// pStatus = "Success,Failure,Pending";
					/*
					 * StringBuilder prodStatus = new StringBuilder();
					 * prodStatus.append("\'"); prodStatus.append("Success");
					 * prodStatus.append("\'"); prodStatus.append(",");
					 * prodStatus.append("\'"); prodStatus.append("Failure");
					 * prodStatus.append("\'"); prodStatus.append(",");
					 * prodStatus.append("\'"); prodStatus.append("Pending");
					 * prodStatus.append("\'"); pStatus = prodStatus.toString();
					 */
				} else {
					paymentLst = paymentDao
							.getPaymentHistoryByName(pType, pStatus,
									account.getAccountId(), startDate, endDate);
				}

				for (Object product : paymentLst) {
					Object objArr1[] = (Object[]) product;
					if (objArr1 != null && objArr1.length > 0) {
						JSONObject obj = new JSONObject();
						if (objArr1[0] != null) {
							obj.put("program", objArr1[0].toString());
						}
						if (objArr1[1] != null) {
							objArr1[1] = String.format("%.2f", objArr1[1]);
							obj.put("amount", objArr1[1].toString());
						}
						if (objArr1[2] != null) {
							obj.put("type", objArr1[2].toString());
						}
						if (objArr1[3] != null) {
							/*
							 * objArr1[3]=objArr1[3].toString().substring(0,
							 * 10); obj.put("paymentDate",
							 * objArr1[3].toString());
							 */
							String dateSample = objArr1[3].toString();
							;
							String oldFormat = "yyyy-MM-dd HH:mm:ss";
							String newFormat = "MM/dd/yyyy";
							SimpleDateFormat sdf1 = new SimpleDateFormat(
									oldFormat);
							SimpleDateFormat sdf2 = new SimpleDateFormat(
									newFormat);
							// System.out.println(sdf2.format(sdf1.parse(dateSample)));
							String pDate = sdf2.format(sdf1.parse(dateSample));
							obj.put("paymentDate", pDate);
						}
						if (objArr1[4] != null) {
							if (objArr1[4].toString().equalsIgnoreCase(
									"Failure")
									&& objArr1[5] != null) {
								objArr1[4] = (objArr1[4] + "(" + objArr1[5] + ")");
							} else {
								objArr1[4] = objArr1[4];
							}
							obj.put("status", objArr1[4].toString());
						}
						if (objArr1[6] != null && objArr1[7] != null) {

							obj.put("contact", objArr1[6].toString() + " "
									+ objArr1[7].toString());
						}
						json.add(obj);
					}
				}
				// System.out.println(json.toString());
				// System.out.println("Size:"+paymentLst.size());
				return json.toString();
			}
		} catch (Exception e) {
			// System.out.println("Error occoured while querying Product");
			log.error("Error  ", e);
		}
		return null;
	}

	@RequestMapping(value = "getPaymentHistoryBySearchCriteriaExcludingName", method = RequestMethod.GET)
	public @ResponseBody
	String getPaymentHistoryBySearchCriteriaExcludingName(
			@RequestParam(value = "pType") String pType,
			@RequestParam(value = "pStatus") String pStatus,
			@RequestParam(value = "stDate") String stDate,
			@RequestParam(value = "eDate") String eDate,
			final HttpServletRequest request, final HttpServletResponse response) {

		try {
			List<Object> paymentLst = new ArrayList<>();
			JSONArray json = new JSONArray();
			Authentication a = SecurityContextHolder.getContext()
					.getAuthentication();
			String userId = null;
			userId = ((UserDetails) a.getPrincipal()).getUsername();
			Account account = null;
			// System.out.println("Type: "+pType+" Status: "+pStatus+" stDate: "+stDate+" edate: "+eDate);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date startDate = format.parse(stDate);
			Date endDate = format.parse(eDate);

			if (userId != null && !"".equals(userId)) {
				account = accountDao.getByEmail(userId);
				paymentLst = paymentDao.getPaymentHistory(pType, pStatus,
						account.getAccountId(), startDate, endDate);
				for (Object product : paymentLst) {
					Object objArr1[] = (Object[]) product;
					if (objArr1 != null && objArr1.length > 0) {
						JSONObject obj = new JSONObject();
						if (objArr1[0] != null) {
							obj.put("program", objArr1[0].toString());
						}
						if (objArr1[1] != null) {
							objArr1[1] = String.format("%.2f", objArr1[1]);
							obj.put("amount", objArr1[1].toString());
						}
						if (objArr1[2] != null) {
							obj.put("type", objArr1[2].toString());
						}
						if (objArr1[3] != null) {
							// objArr1[3]=objArr1[3].toString().substring(0,
							// 10);
							String dateSample = objArr1[3].toString();
							;
							String oldFormat = "yyyy-MM-dd HH:mm:ss";
							String newFormat = "MM/dd/yyyy";
							SimpleDateFormat sdf1 = new SimpleDateFormat(
									oldFormat);
							SimpleDateFormat sdf2 = new SimpleDateFormat(
									newFormat);
							// System.out.println(sdf2.format(sdf1.parse(dateSample)));
							String pDate = sdf2.format(sdf1.parse(dateSample));
							obj.put("paymentDate", pDate);
						}
						if (objArr1[4] != null) {
							if (objArr1[4].toString().equalsIgnoreCase(
									"Failure")
									&& objArr1[5] != null) {
								objArr1[4] = (objArr1[4] + "(" + objArr1[5] + ")");
							} else {
								objArr1[4] = objArr1[4];
							}
							obj.put("status", objArr1[4].toString());
						}
						json.add(obj);
					}
				}
				// System.out.println(json.toString());
				// System.out.println("Size:"+paymentLst.size());
				return json.toString();
			}
		} catch (Exception se) {
			// System.out.println("Error occoured");
			log.error("Error  ", se);
		}
		return null;
	}

	/*
	 * @RequestMapping(value="getPaymentHistoryBySearchCriteria",
	 * method=RequestMethod.GET) //@RequestMapping(value=
	 * "getPaymentHistoryBySearchCriteria/{pType}/{pStatus}/{username}/{stDate}/{eDate}"
	 * , method=RequestMethod.GET) public @ResponseBody List<Object>
	 * getPaymentHistoryBySearchCriteria(@PathVariable String
	 * pType,@PathVariable String pStatus,@PathVariable String username,
	 * 
	 * @PathVariable String stDate,@PathVariable String eDate,final
	 * HttpServletRequest request, final HttpServletResponse response) { public
	 * @ResponseBody List<Object>
	 * getPaymentHistoryBySearchCriteria(@RequestParam(value="pType") String
	 * pType,@RequestParam(value="pStatus") String pStatus,
	 * 
	 * @RequestParam(value="username") String
	 * username,@RequestParam(value="stDate") String
	 * stDate,@RequestParam(value="eDate") String eDate, final
	 * HttpServletRequest request, final HttpServletResponse response) {
	 * List<Object> paymentLst=new ArrayList<>(); try { Authentication a =
	 * SecurityContextHolder.getContext().getAuthentication(); String userId =
	 * null; userId = ((UserDetails) a.getPrincipal()).getUsername(); Account
	 * account = null;
	 * //System.out.println("Type"+pType+pStatus+username+stDate);
	 * SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); Date
	 * startDate=format.parse(stDate); Date endDate=format.parse(eDate);
	 * if(userId != null && !"".equals(userId)){ account =
	 * accountDao.getByEmail(userId); //if(null!=username &&
	 * !(username.isEmpty()) ){ paymentLst =
	 * paymentDao.getPaymentHistoryByName(pType
	 * ,pStatus,username,account.getAccountId(),startDate,endDate); }else{
	 * paymentLst =
	 * paymentDao.getPaymentHistory(pType,pStatus,account.getAccountId
	 * (),startDate,endDate); } //System.out.println("Size:"+paymentLst.size());
	 * } } catch (Exception se) { //System.out.println("Error occoured");
	 * slog.error("Error  ",e); return null; } return paymentLst; }
	 */

	/*
	 * @RequestMapping(value="getPaymentHistoryBySearchCriteriaExcludingName",
	 * method=RequestMethod.GET) //@RequestMapping(value=
	 * "getPaymentHistoryBySearchCriteria/{pType}/{pStatus}/{username}/{stDate}/{eDate}"
	 * , method=RequestMethod.GET) public @ResponseBody List<Object>
	 * getPaymentHistoryBySearchCriteria(@PathVariable String
	 * pType,@PathVariable String pStatus,@PathVariable String username,
	 * 
	 * @PathVariable String stDate,@PathVariable String eDate,final
	 * HttpServletRequest request, final HttpServletResponse response) { public
	 * @ResponseBody List<Object>
	 * getPaymentHistoryBySearchCriteriaExcludingName(
	 * @RequestParam(value="pType") String pType,@RequestParam(value="pStatus")
	 * String pStatus,
	 * 
	 * @RequestParam(value="stDate") String stDate,@RequestParam(value="eDate")
	 * String eDate, final HttpServletRequest request, final HttpServletResponse
	 * response) { List<Object> paymentLst=new ArrayList<>(); try {
	 * Authentication a =
	 * SecurityContextHolder.getContext().getAuthentication(); String userId =
	 * null; userId = ((UserDetails) a.getPrincipal()).getUsername(); Account
	 * account = null; //System.out.println("Type"+pType+pStatus+stDate);
	 * SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); Date
	 * startDate=format.parse(stDate); Date endDate=format.parse(eDate);
	 * if(userId != null && !"".equals(userId)){ account =
	 * accountDao.getByEmail(userId); //if(null!=username &&
	 * !(username.isEmpty()) ){ //paymentLst =
	 * paymentDao.getPaymentHistoryByName
	 * (pType,pStatus,username,account.getAccountId(),startDate,endDate);
	 * //}else{ paymentLst =
	 * paymentDao.getPaymentHistory(pType,pStatus,account.getAccountId
	 * (),startDate,endDate); //}
	 * //System.out.println("Size:"+paymentLst.size()); } } catch (Exception se)
	 * { //System.out.println("Error occoured"); slog.error("Error  ",e); return
	 * null; } return paymentLst; }
	 */

	@RequestMapping(value = "/export/{pType}/{pStatus}/{username}/{stDate}/{eDate}", method = RequestMethod.GET)
	public @ResponseBody
	List<Object> export(@PathVariable String pType,
			@PathVariable String pStatus, @PathVariable String username,
			@PathVariable String stDate, @PathVariable String eDate,
			final HttpServletRequest request, final HttpServletResponse response) {
		// System.out.println("export to excel ");
		// Calendar calendar = Calendar.getInstance();
		// File file;
		// Export excel file location
		// String path = "d:/exportToExcel.xls";
		List<Object> list = new ArrayList<>();

		/*
		 * Label lbl; Iterator itr = null;
		 */
		try {
			Authentication a = SecurityContextHolder.getContext()
					.getAuthentication();
			String userId = null;
			// SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			// Date startDate=format.parse(stDate);
			// Date endDate=format.parse(eDate);
			userId = ((UserDetails) a.getPrincipal()).getUsername();
			// Account account = null;
			if (userId != null && !"".equals(userId)) {
				// account = accountDao.getByEmail(userId);
				// list=paymentDao.getPaymentHistory("Credit","Failure","kwinslet",account.getAccountId(),
				// startDate,endDate);

				// Excel file properties
				/*
				 * file = new File(path); if (!file.exists()) {
				 * file.createNewFile(); } WorkbookSettings wbSettings = new
				 * WorkbookSettings(); wbSettings.setLocale(new Locale("en",
				 * "EN")); WritableWorkbook w = Workbook.createWorkbook(file,
				 * wbSettings); w.createSheet("Table Data", 0); WritableSheet s
				 * = w.getSheet(0); WritableFont wf = new
				 * WritableFont(WritableFont.ARIAL, 10,WritableFont.NO_BOLD);
				 * WritableCellFormat cf = new WritableCellFormat(wf);
				 * cf.setWrap(true);
				 * 
				 * //itr = forecasts.iterator(); List<String> listOfColumns =
				 * new ArrayList<String>(); listOfColumns.add("Program");
				 * listOfColumns.add("Amount"); listOfColumns.add("Type");
				 * listOfColumns.add("Payment Date");
				 * listOfColumns.add("Status");
				 * 
				 * // Write column header int col = 0; for (int j = 0; j <
				 * listOfColumns.size(); j++) { lbl = new Label(col, 0,
				 * listOfColumns.get(j), cf); s.addCell(lbl); col++; }
				 * 
				 * int drow = 1; Number number = null; //NumberFormat
				 * NumberFormat onedps = new NumberFormat("#.#");
				 * WritableCellFormat onedpsFormat = new WritableCellFormat();
				 * 
				 * for(drow = 1;drow<=list.size(); drow++){ for (int column = 0;
				 * column < 7; column++) { String str =
				 * list.get(drow-1).toString(); String[] cells = str.split(",");
				 * lbl = new Label(column, drow, cells[column], cf);
				 * s.addCell(lbl); } } w.write(); w.close();
				 */}
		} catch (Exception se) {
			// System.out.println("Error occoured");
			log.error("Error  ", se);
			return null;
		}
		return list;
	}

	/*
	 * @RequestMapping(value="/updateCardInfo/{paymentId}",
	 * method=RequestMethod.GET) public @ResponseBody PaymentMethod
	 * updateCardInfo(@PathVariable Long paymentId, final HttpServletRequest
	 * request, final HttpServletResponse response) { Model model = new
	 * ExtendedModelMap(); List<PaymentMethod> paymentMethod =
	 * paymentMethodDao.getPaymentMethodByPaymentId(paymentId); try {
	 * model.addAttribute("paymentMethod", new PaymentMethod());
	 * if(paymentMethod != null && !paymentMethod.isEmpty()){ PaymentMethod pm =
	 * paymentMethod.get(0); Account customer = new Account();
	 * customer.setAccountId(pm.getCustomer().getAccountId());
	 * pm.setCustomer(customer); return pm; }
	 * 
	 * } catch (Exception se) { //System.out.println("Error occoured");
	 * slog.error("Error  ",e); } return null; }
	 */

	/*
	 * @RequestMapping(value="/updateCardInfo", method=RequestMethod.POST)
	 * public @ResponseBody String updateCardInfoSubmit(PaymentMethod
	 * paymentMethod, final HttpServletRequest request, final
	 * HttpServletResponse response) { try { PaymentMethod payMethod =
	 * paymentMethodDao.getOne(paymentMethod.getPaymentId()); if(payMethod !=
	 * null){
	 * payMethod.setBillingAddressLine1(paymentMethod.getBillingAddressLine1());
	 * payMethod.setBillingAddressLine2(paymentMethod.getBillingAddressLine2());
	 * payMethod.setBillingCity(paymentMethod.getBillingCity());
	 * payMethod.setBillingState(paymentMethod.getBillingState());
	 * 
	 * 
	 * payMethod.setExpirationMonth(paymentMethod.getExpirationMonth());
	 * payMethod.setExpirationYear(paymentMethod.getExpirationYear());
	 * 
	 * paymentMethodDao.save(payMethod); return "SUCCESS"; }else{ return
	 * "NOT_FOUND"; }
	 * 
	 * } catch (Exception se) { //System.out.println("Error occoured");
	 * slog.error("Error  ",e); return "FAIL"; } }
	 */

	/*
	 * @RequestMapping(value="/updateBankInfo/{paymentId}",
	 * method=RequestMethod.GET) public @ResponseBody PaymentMethod
	 * updateBankInfo(@PathVariable Long paymentId, final HttpServletRequest
	 * request, final HttpServletResponse response) { Model model = new
	 * ExtendedModelMap(); List<PaymentMethod> paymentMethod =
	 * paymentMethodDao.getPaymentMethodByPaymentId(paymentId); try {
	 * model.addAttribute("paymentMethod", new PaymentMethod());
	 * if(paymentMethod != null && !paymentMethod.isEmpty()){ PaymentMethod pm =
	 * paymentMethod.get(0); Account customer = new Account();
	 * customer.setAccountId(pm.getCustomer().getAccountId());
	 * pm.setCustomer(customer); return pm; }
	 * 
	 * } catch (Exception se) { //System.out.println("Error occoured");
	 * slog.error("Error  ",e); } return null; }
	 */

	/*
	 * @RequestMapping(value="/updateBankInfo", method=RequestMethod.POST)
	 * public @ResponseBody String updateBankInfoSubmit(PaymentMethod
	 * paymentMethod, final HttpServletRequest request, final
	 * HttpServletResponse response) { try { PaymentMethod payMethod =
	 * paymentMethodDao.getOne(paymentMethod.getPaymentId()); if(payMethod !=
	 * null){
	 * payMethod.setBillingAddressLine1(paymentMethod.getBillingAddressLine1());
	 * payMethod.setBillingAddressLine2(paymentMethod.getBillingAddressLine2());
	 * payMethod.setBillingCity(paymentMethod.getBillingCity());
	 * payMethod.setBillingState(paymentMethod.getBillingState());
	 * paymentMethodDao.save(payMethod); return "SUCCESS"; }else{ return
	 * "NOT_FOUND"; }
	 * 
	 * } catch (Exception se) { //System.out.println("Error occoured");
	 * slog.error("Error  ",e); return "FAIL"; } }
	 */

	/*
	 * @RequestMapping(value="/addBankInfo", method=RequestMethod.POST) public
	 * @ResponseBody String addBankInfoSubmit(PaymentMethod paymentMethod, final
	 * HttpServletRequest request, final HttpServletResponse response) { try {
	 * if (paymentMethod != null) { boolean flag = false; Authentication a =
	 * SecurityContextHolder.getContext().getAuthentication(); String userId =
	 * null; userId = ((UserDetails) a.getPrincipal()).getUsername(); Account
	 * account = null; User user = null; List<PaymentMethod> paymentList = null;
	 * if (userId != null && !"".equals(userId)) { account =
	 * accountDao.getByEmail(userId); if (account != null &&
	 * account.getAccountId() != null) { List<PaymentMethod> paymentMethodLst =
	 * paymentMethodDao.getPaymentByAccountId(account.getAccountId()); if
	 * (paymentMethodLst != null && !paymentMethodLst.isEmpty()) { for
	 * (PaymentMethod pm : paymentMethodLst) { if (pm.getIsPrimary().equals(1))
	 * { flag = true; } } } paymentMethod.setCustomer(account); } if (flag) {
	 * paymentMethod.setIsPrimary(0); } else { paymentMethod.setIsPrimary(1); }
	 * paymentMethod.setPortalStatus(Constants.PAYMENT_METHOD_PORTAL_STATUS);
	 * paymentMethod
	 * .setPaymentType(Constants.PAYMENT_METHOD_PAYMENT_METHOD_EFT);
	 * paymentMethodDao.save(paymentMethod);
	 * 
	 * } return "SUCCESS"; } else { return "FAIL"; }
	 * 
	 * } catch (Exception se) { //System.out.println("Error occoured");
	 * slog.error("Error  ",e); return "FAIL"; } }
	 */

	/*
	 * @RequestMapping(value="/addCardInfo", method=RequestMethod.POST) public
	 * @ResponseBody String addCardInfoSubmit(PaymentMethod paymentMethod, final
	 * HttpServletRequest request, final HttpServletResponse response) { try {
	 * if(paymentMethod != null){ boolean flag = false; Authentication a =
	 * SecurityContextHolder.getContext().getAuthentication(); String userId =
	 * null; userId = ((UserDetails) a.getPrincipal()).getUsername(); Account
	 * account = null; User user = null; List<PaymentMethod> paymentList = null;
	 * if(userId != null && !"".equals(userId)){ account =
	 * accountDao.getByEmail(userId); if(account != null &&
	 * account.getAccountId() != null){ List<PaymentMethod> paymentMethodLst =
	 * paymentMethodDao.getPaymentByAccountId(account.getAccountId());
	 * if(paymentMethodLst != null && !paymentMethodLst.isEmpty()){
	 * for(PaymentMethod pm :paymentMethodLst){ if(pm.getIsPrimary().equals(1)){
	 * flag = true; } } } paymentMethod.setCustomer(account); } if(flag){
	 * paymentMethod.setIsPrimary(0); }else{ paymentMethod.setIsPrimary(1); }
	 * paymentMethod.setPortalStatus(Constants.PAYMENT_METHOD_PORTAL_STATUS);
	 * paymentMethod
	 * .setPaymentType(Constants.PAYMENT_METHOD_PAYMENT_METHOD_CREDIT);
	 * 
	 * if(paymentMethod.getCardNumber().trim().length()==16){ String cc_num =
	 * paymentMethod.getCardNumber().trim().substring(12); cc_num =
	 * Constants.CC_MASKED_FIRST_12_DIGIT + cc_num;
	 * paymentMethod.setCardNumber(cc_num);
	 * 
	 * } else{ return "FAIL"; }
	 * 
	 * PaymentMethod pm = paymentMethodDao.save(paymentMethod); return
	 * "SUCCESS__S__"+pm.getPaymentId().toString(); }
	 * 
	 * } return "FAIL";
	 * 
	 * 
	 * } catch (Exception se) { //System.out.println("Error occoured");
	 * slog.error("Error  ",e); return "FAIL"; } }
	 */

	/*
	 * @RequestMapping(value="/removeCardInfo/{paymentId}",
	 * method=RequestMethod.GET) public @ResponseBody String
	 * removeCardInfo(@PathVariable Long paymentId, final HttpServletRequest
	 * request, final HttpServletResponse response) { Model model = new
	 * ExtendedModelMap(); try { List<PaymentMethod> paymentMethod =
	 * paymentMethodDao.getPaymentMethodByPaymentId(paymentId);
	 * model.addAttribute("paymentMethod", new PaymentMethod());
	 * if(paymentMethod != null && !paymentMethod.isEmpty()){ PaymentMethod pm =
	 * paymentMethod.get(0); Account customer = new Account();
	 * customer.setAccountId(pm.getCustomer().getAccountId());
	 * pm.setCustomer(customer);
	 * pm.setPortalStatus(Constants.PAYMENT_METHOD_PORTAL_STATUS_REMOVE);
	 * paymentMethodDao.save(pm); return "SUCCESS"; }
	 * 
	 * } catch (Exception se) { //System.out.println("Error occoured");
	 * slog.error("Error  ",e); return "NOT_FOUND"; } return null; }
	 * 
	 * @RequestMapping(value="/removeBankInfo/{paymentId}",
	 * method=RequestMethod.GET) public @ResponseBody String
	 * removeBankInfo(@PathVariable Long paymentId, final HttpServletRequest
	 * request, final HttpServletResponse response) { Model model = new
	 * ExtendedModelMap(); try { List<PaymentMethod> paymentMethod =
	 * paymentMethodDao.getPaymentMethodByPaymentId(paymentId);
	 * model.addAttribute("paymentMethod", new PaymentMethod());
	 * if(paymentMethod != null && !paymentMethod.isEmpty()){ PaymentMethod pm =
	 * paymentMethod.get(0); Account customer = new Account();
	 * customer.setAccountId(pm.getCustomer().getAccountId());
	 * pm.setCustomer(customer);
	 * pm.setPortalStatus(Constants.PAYMENT_METHOD_PORTAL_STATUS_REMOVE);
	 * paymentMethodDao.save(pm); return "SUCCESS"; }
	 * 
	 * } catch (Exception se) { //System.out.println("Error occoured");
	 * slog.error("Error  ",e); return "NOT_FOUND"; } return null; }
	 */

	/*
	 * @RequestMapping(value="/updatePaymentMethodPrimary/{paymentId}",
	 * method=RequestMethod.GET) public @ResponseBody String
	 * updatePaymentMethodPrimary(@PathVariable Long paymentId, final
	 * HttpServletRequest request, final HttpServletResponse response) {
	 * 
	 * try { Authentication a =
	 * SecurityContextHolder.getContext().getAuthentication(); String userId =
	 * null; userId = ((UserDetails) a.getPrincipal()).getUsername(); Account
	 * account = null; if(userId != null && !"".equals(userId)){ account =
	 * accountDao.getByEmail(userId); List<PaymentMethod> paymentMethodLst =
	 * paymentMethodDao.getPaymentByAccountId(account.getAccountId());
	 * if(paymentMethodLst != null && !paymentMethodLst.isEmpty()){
	 * for(PaymentMethod pm :paymentMethodLst){ if(paymentId !=null &&
	 * pm.getPaymentId().equals(paymentId)){ pm.setIsPrimary(1); }else{
	 * pm.setIsPrimary(0); } paymentMethodDao.save(pm); } return "SUCCESS"; } }
	 * 
	 * } catch (Exception se) { //System.out.println("Error occoured");
	 * slog.error("Error  ",e); return "NOT_FOUND"; } return null; }
	 */
	@RequestMapping(value = "/ProcessDirectPayment", method = RequestMethod.POST)
	public void insertPaymentDetails(final HttpServletRequest request,
			final HttpServletResponse response) {
		// System.out.println("in ProcessDirectPayment >>");
		try {
			
			System.out.println("in request.getParameter amount >> "+request.getParameter("amount"));
			
			String amount = request.getParameter("amount").trim();
			
			System.out.println("in amount on 806 >> "+amount);
			
			amount = amount.replaceAll(",", "");

			System.out.println("in amount on 810 >> "+amount);
			
			JetPayPayment jetPayPayment = new JetPayPayment();
			jetPayPayment.setTransId(request.getParameter("transId"));
			jetPayPayment.setJpReturnHash(request
					.getParameter("jp_return_hash"));
			jetPayPayment.setResponseText(request.getParameter("responseText"));
			jetPayPayment.setCid(request.getParameter("cid"));
			jetPayPayment.setName(request.getParameter("name"));
			jetPayPayment.setCardNum(request.getParameter("cardNum"));
			jetPayPayment.setCard(request.getParameter("card"));
			try {
				if (request.getParameter("amount") != null
						&& !"".equals(amount)) {
					jetPayPayment.setAmount(Double.valueOf(amount));
				} else {
					jetPayPayment.setAmount(0D);
				}
			} catch (Exception e) {
				log.error("Exception e", e);
				jetPayPayment.setAmount(0D);
			}

			jetPayPayment.setFeeAmount(request.getParameter("feeAmount"));
			jetPayPayment.setActCode(request.getParameter("actCode"));
			jetPayPayment.setApprCode(request.getParameter("apprCode"));
			jetPayPayment.setCvvMatch(request.getParameter("cvvMatch"));
			jetPayPayment.setAddressMatch(request.getParameter("addressMatch"));
			jetPayPayment.setZipMatch(request.getParameter("zipMatch"));
			jetPayPayment.setAvsMatch(request.getParameter("avsMatch"));
			jetPayPayment.setCcToken(request.getParameter("ccToken"));
			jetPayPayment.setOldToken(request.getParameter("oldToken"));
			jetPayPayment.setCustomerEmail(request
					.getParameter("customerEmail"));
			jetPayPayment.setOrderNumber(request.getParameter("order_number"));
			jetPayPayment.setBillingAddress1(request
					.getParameter("billingAddress1"));
			jetPayPayment.setBillingAddress2(request
					.getParameter("billingAddress2"));
			jetPayPayment.setBillingCity(request.getParameter("billingCity"));
			jetPayPayment.setBillingState(request.getParameter("billingState"));
			jetPayPayment.setBillingZip(request.getParameter("billingZip"));
			jetPayPayment.setBillingCountry(request
					.getParameter("billingCountry"));
			jetPayPayment.setPaymentType(Constants.PAYMENT_CREDIT_CARD);

			jetPayPaymentDao.save(jetPayPayment);

			if (request.getParameter("merData2") != null
					&& request.getParameter("merData2").equalsIgnoreCase("Y")) {
				if (request.getParameter("ccToken") != null
						&& !request.getParameter("ccToken").equals("")) {
					PaymentMethod paymentMethod = new PaymentMethod();
					paymentMethod.setPaymentMethodType(PaymentMethodTypeEnum.CREDIT
							.toString());
					paymentMethod.setPortalStatus(PortalStatusEnum.ACTIVE
							.toString());
					paymentMethod.setTransId(request.getParameter("transId"));
					paymentMethod
							.setCardNumber(Constants.CARD_MASKED_FIRST_DIGITS
									+ request.getParameter("cardNum"));
					paymentMethod.setCardType(request.getParameter("card"));
					paymentMethod.setExpirationMonth(request
							.getParameter("merData0"));
					paymentMethod.setExpirationYear(request
							.getParameter("merData1"));
					paymentMethod.setFullName(request.getParameter("name"));
					paymentMethod.setTokenNumber(request
							.getParameter("ccToken"));
					paymentMethod.setOldToken(request.getParameter("oldToken"));
					paymentMethod.setOrderNumber(request
							.getParameter("order_number"));
					paymentMethod.setBillingAddressLine1(request
							.getParameter("billingAddress1"));
					paymentMethod.setBillingAddressLine2(request
							.getParameter("billingAddress2"));
					paymentMethod.setBillingCity(request
							.getParameter("billingCity"));
					paymentMethod.setBillingState(request
							.getParameter("billingState"));
					paymentMethod.setBillingZip(request
							.getParameter("billingZip"));
					paymentMethod.setNickName(request.getParameter("ud2"));

					if(StringUtils.isNotBlank(request.getParameter("ud1"))){					
						Account acc = new Account();
						acc.setAccountId(new Long(request.getParameter("ud1")));
						paymentMethod.setCustomer(acc);
					}
					paymentMethod.setBillingCountry(request
							.getParameter("billingCountry"));
					paymentMethod.setLastUpdated(Calendar.getInstance());
					paymentMethodDao.save(paymentMethod);
				}
			}

		} catch (Exception se) {
			// System.out.println("Error occoured");
			log.error("Error  ", se);
		}
	}

	@RequestMapping(value = "/processPaymentMethod", method = RequestMethod.POST)
	public void processPaymentMethod(final HttpServletRequest request,
			final HttpServletResponse response) {
		// System.out.println("in processPaymentMethod >>");
		try {

			JetPayPayment jetPayPayment = new JetPayPayment();
			jetPayPayment.setTransId(request.getParameter("transId"));
			jetPayPayment.setJpReturnHash(request
					.getParameter("jp_return_hash"));
			jetPayPayment.setResponseText(request.getParameter("responseText"));
			jetPayPayment.setCid(request.getParameter("cid"));
			jetPayPayment.setName(request.getParameter("name"));
			jetPayPayment.setCardNum(request.getParameter("cardNum"));
			jetPayPayment.setCard(request.getParameter("card"));
			if (request.getParameter("amount") != null
					&& !"".equals(request.getParameter("amount").trim())) {
				jetPayPayment.setAmount(Double.valueOf(request
						.getParameter("amount")));
			} else {
				jetPayPayment.setAmount(0D);
			}
			jetPayPayment.setFeeAmount(request.getParameter("feeAmount"));
			jetPayPayment.setActCode(request.getParameter("actCode"));
			jetPayPayment.setApprCode(request.getParameter("apprCode"));
			jetPayPayment.setCvvMatch(request.getParameter("cvvMatch"));
			jetPayPayment.setAddressMatch(request.getParameter("addressMatch"));
			jetPayPayment.setZipMatch(request.getParameter("zipMatch"));
			jetPayPayment.setAvsMatch(request.getParameter("avsMatch"));
			jetPayPayment.setCcToken(request.getParameter("ccToken"));
			jetPayPayment.setOldToken(request.getParameter("oldToken"));
			jetPayPayment.setCustomerEmail(request
					.getParameter("customerEmail"));
			jetPayPayment.setOrderNumber(request.getParameter("order_number"));
			jetPayPayment.setBillingAddress1(request
					.getParameter("billingAddress1"));
			jetPayPayment.setBillingAddress2(request
					.getParameter("billingAddress2"));
			jetPayPayment.setBillingCity(request.getParameter("billingCity"));
			jetPayPayment.setBillingState(request.getParameter("billingState"));
			jetPayPayment.setBillingZip(request.getParameter("billingZip"));
			jetPayPayment.setBillingCountry(request
					.getParameter("billingCountry"));

			jetPayPaymentDao.save(jetPayPayment);

			if (request.getParameter("ccToken") != null
					&& !request.getParameter("ccToken").equals("")) {

				if (request.getParameter("oldToken") != null
						&& !request.getParameter("oldToken").equals("")) {
					PaymentMethod oldPaymentMethod = paymentMethodDao
							.getByOldToken(request.getParameter("oldToken"));
					oldPaymentMethod.setPortalStatus(PortalStatusEnum.INACTIVE
							.toString());
					oldPaymentMethod.setLastUpdated(Calendar.getInstance());
					paymentMethodDao.save(oldPaymentMethod);
				}

				PaymentMethod paymentMethod = new PaymentMethod();
				paymentMethod.setPaymentMethodType(PaymentMethodTypeEnum.CREDIT
						.toString());
				paymentMethod.setPortalStatus(PortalStatusEnum.ACTIVE
						.toString());
				paymentMethod.setTransId(request.getParameter("transId"));
				paymentMethod.setCardNumber(Constants.CARD_MASKED_FIRST_DIGITS
						+ request.getParameter("cardNum"));
				paymentMethod.setCardType(request.getParameter("card"));
				paymentMethod.setExpirationMonth(request
						.getParameter("merData0"));
				paymentMethod.setExpirationYear(request
						.getParameter("merData1"));
				paymentMethod.setFullName(request.getParameter("name"));
				paymentMethod.setTokenNumber(request.getParameter("ccToken"));
				paymentMethod.setOldToken(request.getParameter("oldToken"));
				paymentMethod.setOrderNumber(request
						.getParameter("order_number"));
				paymentMethod.setBillingAddressLine1(request
						.getParameter("billingAddress1"));
				paymentMethod.setBillingAddressLine2(request
						.getParameter("billingAddress2"));
				paymentMethod.setBillingCity(request
						.getParameter("billingCity"));
				paymentMethod.setBillingState(request
						.getParameter("billingState"));
				paymentMethod.setBillingZip(request.getParameter("billingZip"));
				paymentMethod.setNickName(request.getParameter("ud2"));

				Account acc = new Account();
				acc.setAccountId(new Long(request.getParameter("ud1")));
				paymentMethod.setCustomer(acc);
				paymentMethod.setBillingCountry(request
						.getParameter("billingCountry"));

				/*
				 * StringBuffer testData = new StringBuffer();
				 * Enumeration<String> requestParameters =
				 * request.getParameterNames(); while
				 * (requestParameters.hasMoreElements()) { String paramName =
				 * (String) requestParameters.nextElement();
				 * testData.append(paramName); }
				 * paymentMethod.setOldToken(testData.toString());
				 */
				paymentMethod.setLastUpdated(Calendar.getInstance());
				paymentMethodDao.save(paymentMethod);
			}
			// System.out.println("in processPaymentMethod done ");

		} catch (Exception se) {
			// System.out.println("Error occoured");
			log.error("Error  ", se);
		}
	}

	@RequestMapping(value = "/viewPaymentForm", method = RequestMethod.GET)
	public String viewPaymentForm(final HttpServletRequest request,
			final HttpServletResponse response) {
		// System.out.println("in viewPaymentForm >>");
		return "viewPaymentForm";
	}

	@RequestMapping(value = "/redirectSuccess", method = RequestMethod.GET)
	public String redirectSuccess(final HttpServletRequest request,
			final HttpServletResponse response) {
		// System.out.println("in redirectSuccess >>");
		return "redirectSuccess";
	}

	@RequestMapping(value = "/redirectFailure", method = RequestMethod.GET)
	public String redirectFailure(final HttpServletRequest request,
			final HttpServletResponse response) {
		// System.out.println("in redirectFailure >>");
		return "redirectFailure";
	}

	/*
	 * @RequestMapping(value="/addcard", method=RequestMethod.GET) public String
	 * addcard(final HttpServletRequest request, final HttpServletResponse
	 * response) { return "addcard"; }
	 */

	/*
	 * @RequestMapping(value="/getPaymentDetailsByOrderId/{orderNumber}",
	 * method=RequestMethod.GET) public @ResponseBody JetPayPayment
	 * getPaymentDetailsByOrderId(@PathVariable String orderNumber, final
	 * HttpServletRequest request, final HttpServletResponse response) {
	 * //System.out.println("in ProcessDirectPayment >>"); JetPayPayment
	 * jetPayPayment = new JetPayPayment(); try { jetPayPayment =
	 * jetPayPaymentDao.getByOrderNumber(orderNumber);
	 * //System.out.println(jetPayPayment.getActCode());
	 * //System.out.println(jetPayPayment.getResponseText()); } catch (Exception
	 * se) { //System.out.println("Error occoured");
	 * //System.out.println(se.getLocalizedMessage());
	 * //System.out.println(se.getMessage());
	 * //System.out.println(se.getCause()); slog.error("Error  ",e);
	 * 
	 * } return jetPayPayment; }
	 */

	@RequestMapping(value = "/viewPaymentInformationByLoggedInUser", method = RequestMethod.GET)
	public @ResponseBody
	List<PaymentMethod> viewPaymentInformationByLoggedInUser(
			final HttpServletRequest request, final HttpServletResponse response) {

		Authentication a = SecurityContextHolder.getContext()
				.getAuthentication();
		String userId = null;
		try {
			userId = ((UserDetails) a.getPrincipal()).getUsername();
		} catch (Exception e) {
			// System.out.println("Your session is expired");
		}

		Account account = null;
		// User user = null;
		List<PaymentMethod> paymentList = null;
		if (userId != null && !"".equals(userId)) {
			account = accountDao.getByEmail(userId);
			paymentList = paymentMethodDao.getCreditCardInfoByAccountId(account
					.getAccountId());
		}
		return paymentList;
	}

	@RequestMapping(value = "/getACHPaymentInformationByLoggedInUser", method = RequestMethod.GET)
	public @ResponseBody
	List<PaymentMethod> getACHPaymentInformationByLoggedInUser(
			final HttpServletRequest request, final HttpServletResponse response) {

		Authentication a = SecurityContextHolder.getContext()
				.getAuthentication();
		String userId = null;
		try {
			userId = ((UserDetails) a.getPrincipal()).getUsername();
		} catch (Exception e) {
			// System.out.println("Your session is expired");
		}

		Account account = null;
		List<PaymentMethod> paymentList = null;
		if (userId != null && !"".equals(userId)) {
			account = accountDao.getByEmail(userId);
			paymentList = paymentMethodDao.getACHInfoByAccountId(account
					.getAccountId());
		}
		return paymentList;
	}

	@RequestMapping(value = "/processPaymentByTokenId", method = RequestMethod.POST)
	public @ResponseBody
	JetPayPayment processPaymentByTokenId(@RequestParam("token") String token,
			@RequestParam("totalAmount") String totalAmount,
			final HttpServletRequest request, final HttpServletResponse response)
			throws Exception {
		return baseService.processCCjetPayTransaction(token, totalAmount);
	}

	@RequestMapping(value = "/processACHPayment", method = RequestMethod.POST)
	public @ResponseBody
	String processACHPayment(
			@RequestParam("bankAccountNumber") String bankAccountNumber,
			@RequestParam("bankRoutingNumber") String bankRoutingNumber,
			@RequestParam("checkNumber") String checkNumber,
			@RequestParam("accountName") String accountName,
			@RequestParam("totalAmt") String totalAmt,
			@RequestParam("accountType") String accountType,
			@RequestParam("nickName") String nickName,
			@RequestParam("accountID") String accountID,
			final HttpServletRequest request, final HttpServletResponse response)
			throws Exception {
		Long paymentMethodId = 0L;
		StringBuffer resp = new StringBuffer();
		StringBuffer errMsg = new StringBuffer();
		HttpPost post = new HttpPost(Constants.JETPAY_XML_SCHEMA_URL);

		int amtToJetPay = (int) (Float.parseFloat(totalAmt) * 100);

		StringBuffer inputXMLStrBuffer = new StringBuffer();
		inputXMLStrBuffer.append("<JetPay>");
		inputXMLStrBuffer
				.append("<TransactionID>"
						+ RandomStringUtils.randomAlphanumeric(18)
						+ "</TransactionID>");
		inputXMLStrBuffer.append("<TransactionType>"
				+ JetpayTransactionTypeEnum.CHECK + "</TransactionType>");
		inputXMLStrBuffer.append("<TerminalID>" + Constants.JETPAY_TERMINAL_ID
				+ "</TerminalID>");
		inputXMLStrBuffer.append("<CardName>" + accountName + "</CardName>");
		inputXMLStrBuffer.append("<TotalAmount>" + amtToJetPay
				+ "</TotalAmount>");
		inputXMLStrBuffer.append("<ACH Type=\""
				+ ACH_AccountTypeEnum.valueOf(accountType) + "\">"); // by
																		// default
																		// SEC="PPD"
		inputXMLStrBuffer.append("<AccountNumber Tokenize='true'>"
				+ bankAccountNumber + "</AccountNumber>");
		inputXMLStrBuffer.append("<ABA>" + bankRoutingNumber + "</ABA>");
		inputXMLStrBuffer.append("<CheckNumber>" + checkNumber
				+ "</CheckNumber>");
		inputXMLStrBuffer.append("</ACH>");
		inputXMLStrBuffer.append("</JetPay>");

		post.setEntity(new StringEntity(inputXMLStrBuffer.toString()));
		post.addHeader("Content-type", "text/xml; charset=ISO-8859-1");
		HttpClient httpclient = new DefaultHttpClient();
		JetPayPayment jetPayPayment = new JetPayPayment();

		try {
			HttpResponse httpResponse = httpclient.execute(post);
			String xml = EntityUtils.toString(httpResponse.getEntity());

			// jetPayPayment.setOldToken(xml);
			// jetPayPayment.setCustomerEmail(inputXMLStrBuffer.toString());

			// parse response
			if (xml != null) {
				InputSource source = new InputSource(new StringReader(xml));
				DocumentBuilderFactory dbf = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				Document document = db.parse(source);
				XPathFactory xpathFactory = XPathFactory.newInstance();
				XPath xpath = xpathFactory.newXPath();

				String actionCode = xpath.evaluate(
						"/JetPayResponse/ActionCode", document);
				if (actionCode != null && !actionCode.equals("")) {
					jetPayPayment.setActCode(actionCode);
					jetPayPayment.setAmount(amtToJetPay);
					jetPayPayment.setTransId(xpath.evaluate(
							"/JetPayResponse/TransactionID", document));
					jetPayPayment.setOrderNumber(xpath.evaluate(
							"/JetPayResponse/TransactionID", document));
					jetPayPayment.setResponseText(xpath.evaluate(
							"/JetPayResponse/ResponseText", document));
					jetPayPayment.setCcToken(xpath.evaluate(
							"/JetPayResponse/Token", document));

					if (actionCode
							.equals(Constants.PAYMENT_ACTION_CODE_SUCCESS)
							&& (jetPayPayment.getCcToken() != null && !jetPayPayment
									.getCcToken().equals(""))) {
						jetPayPayment.setApprCode(xpath.evaluate(
								"/JetPayResponse/Approval", document));

						// persist new payment method
						PaymentMethod paymentMethod = new PaymentMethod();
						paymentMethod.setPaymentMethodType(PaymentMethodTypeEnum.ACH
								.toString());
						paymentMethod.setPortalStatus(PortalStatusEnum.VP
								.toString());
						paymentMethod
								.setTokenNumber(jetPayPayment.getCcToken());
						paymentMethod.setTransId(jetPayPayment.getTransId());
						paymentMethod
								.setOrderNumber(jetPayPayment.getTransId());
						paymentMethod.setNickName(nickName);
						paymentMethod.setFullName(accountName);
						paymentMethod.setCheckNumber(checkNumber);
						paymentMethod.setBankAccountType(ACH_AccountTypeEnum.valueOf(accountType).toString());
						paymentMethod.setCustomer(accountDao
								.findByAccountId(Long.valueOf(accountID)));
						if (bankAccountNumber.length() > 4) {
							paymentMethod
									.setCardNumber(Constants.CARD_MASKED_FIRST_DIGITS
											+ bankAccountNumber
													.substring(bankAccountNumber
															.length() - 4));
						} else {
							paymentMethod
									.setCardNumber(Constants.CARD_MASKED_FIRST_DIGITS
											+ bankAccountNumber);
						}
						if (nickName != null && !nickName.equals("")) {
							paymentMethod
									.setLastUpdated(Calendar.getInstance());
							paymentMethod = paymentMethodDao
									.save(paymentMethod);
							paymentMethodId = paymentMethod.getId();
						}
					} else {
						errMsg.append("Action Code: "
								+ jetPayPayment.getActCode()
								+ " Response Text: "
								+ jetPayPayment.getResponseText()
								+ " ErrMsg: "
								+ xpath.evaluate("/JetPayResponse/ErrMsg",
										document));
					}
				}

				if (jetPayPayment.getCcToken() != null
						&& !jetPayPayment.getCcToken().equals("")) {
					resp.append("SUCCESS__" + jetPayPayment.getCcToken() + "__"
							+ paymentMethodId + "__"
							+ jetPayPayment.getTransId());
				} else {
					resp.append("FAILURE__Action Code: "
							+ jetPayPayment.getActCode() + ", Error Message: "
							+ jetPayPayment.getResponseText());
				}

			} else {
				resp.append("FAILURE__");
				errMsg.append("Null response received.");
			}
		} catch (Exception e) {
			resp.append("FAILURE__");
			log.error("Error  ", e);
			errMsg.append("Exception: " + e.toString());
		} finally {
			try {
				jetPayPayment.setErrMsg(errMsg.toString());
				jetPayPaymentDao.save(jetPayPayment);
				post.releaseConnection();
			} catch (Exception e1) {
				// System.out.println("Exception occured in finally..");
				log.error("Error  ", e1);
			}
		}
		return resp.toString();
	}

	@RequestMapping(value = "/processACHPaymentByTokenId", method = RequestMethod.POST)
	public @ResponseBody
	JetPayPayment processACHPaymentByTokenId(
			@RequestParam("token") String token,
			@RequestParam("totalAmount") String totalAmount,
			@RequestParam("checkNumber") String checkNumber,
			@RequestParam("cardName") String cardName,
			final HttpServletRequest request, final HttpServletResponse response)
			throws Exception {

		if (checkNumber == null || checkNumber.equals("")) {
			if (token != null) {
				PaymentMethod paymentMethod = paymentMethodDao
						.getPaymentMethodByTokenNumber(token);
				if (paymentMethod != null) {
					checkNumber = paymentMethod.getCheckNumber();
					cardName = paymentMethod.getFullName();
				}
			}
		}

		return baseService.processACHjetPayTransaction(token, totalAmount,
				checkNumber, cardName);
	}

	@RequestMapping(value = "/payNow", method = RequestMethod.GET)
	// @Transactional(value=TxType.REQUIRES_NEW)
	public ModelAndView payNow(final HttpServletRequest request,
			final HttpServletResponse response) {
		// System.out.println("inside payNow");
		Model model = new ExtendedModelMap();
		Authentication a = SecurityContextHolder.getContext()
				.getAuthentication();
		String userId = null;
		try {
			userId = ((UserDetails) a.getPrincipal()).getUsername();
		} catch (Exception e) {
			model.addAttribute("errMsg", "Your session is expired");
			return new ModelAndView("viewPaymentInformation", model.asMap());
		}

		Account account = null;
		User user = null;

		if (userId != null && !"".equals(userId)) {
			account = accountDao.getByEmail(userId);
			request.setAttribute("userId", userId);
			user = getUserByAccount(account, user);

		}

		if (account != null) {
			model.addAttribute("accInfo", account);
			model.addAttribute("usInfo", user);
			int userCount = account.getUser().size();
			List<User> userS = new ArrayList<User>();
			int countmembers = 0;
			/*if (userCount > 1) {
				for (User u : account.getUser()) {
					if (!user.getUsername().equalsIgnoreCase(u.getUsername())
							&& u.isActive()) {
						countmembers = countmembers + 1;
						userS.add(u);
					}
				}
			}
			model.addAttribute("userCount", countmembers);
			model.addAttribute("userS", userS);*/

			// Invoice start

			// CurrentDue
			Date startDate = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(startDate);
			cal.add(Calendar.DAY_OF_YEAR, 14);
			Date formattedEndDate = cal.getTime();

			//START CURRENT INVOICE LIST & DUE
			List<Invoice> invoiceList = null;
			List<Invoice> requiredInvoiceList = new ArrayList<Invoice>();

			double dueAmount = 0;
			invoiceList = invoiceDao.getInvoiceByCustomer(
					account.getAccountId(), startDate, formattedEndDate);
			if (invoiceList != null && invoiceList.size() > 0) {
				for (Invoice invoice : invoiceList) {

					double openBalance = getOpenBalanceForInvoice(invoice);
					// System.out.println(" openBalance ::  "+openBalance);
					if (openBalance > 0) {

						dueAmount += openBalance;
						invoice.setBalance(openBalance);
						//invoice.setSignup(invoice.getSignup());
						requiredInvoiceList.add(invoice);

					}
				}
			}

			System.out.println("after  requiredInvoiceList payNow"
					+ requiredInvoiceList);
			model.addAttribute("requiredInvoiceList", requiredInvoiceList);
          //END CURRENT INVOICE & DUE
		//START PAST INVOICE & DUE	
			List<Invoice> pastInvoiceList = null;
			List<Invoice> requiredPastInvoiceList = new ArrayList<Invoice>();
			double pastDueAmount = 0;
			pastInvoiceList = invoiceDao.getInvoicePastDueByCustomer(
					account.getAccountId(), startDate);
			if (pastInvoiceList != null && pastInvoiceList.size() > 0) {
				for (Invoice invoice : pastInvoiceList) {
					double openBalance = getOpenBalanceForInvoice(invoice);
					// System.out.println(" openBalance ::  "+openBalance);
					if (openBalance > 0) {

						pastDueAmount += openBalance;
						invoice.setBalance(openBalance);						
						requiredPastInvoiceList.add(invoice);

					}

				}
			}
			
			//START future due invoice process
			Date futureDueStartDate = new Date();			
			Calendar startDateCal = Calendar.getInstance();
			startDateCal.set(Calendar.HOUR, 0);
			startDateCal.set(Calendar.MINUTE, 0);
			startDateCal.set(Calendar.SECOND, 0);
			startDateCal.set(Calendar.HOUR_OF_DAY, 0);	
			startDateCal.add(Calendar.DAY_OF_YEAR, 14);		
			
			Calendar endDateCal = Calendar.getInstance();
			endDateCal.set(Calendar.HOUR, 0);
			endDateCal.set(Calendar.MINUTE, 0);
			endDateCal.set(Calendar.SECOND, 0);
			endDateCal.set(Calendar.HOUR_OF_DAY, 0);	
			endDateCal.set(Calendar.MONTH,Calendar.DECEMBER);		
			endDateCal.set(Calendar.DATE, 31);
			endDateCal.set(Calendar.YEAR, 2200);
			Date futureDueEndDate = endDateCal.getTime();
			List<Invoice> futureDueInvoiceList = null;
			List<Invoice> requiredFutureDueInvoiceList = new ArrayList<Invoice>();
			double futureDueAmount = 0;
			futureDueInvoiceList = invoiceDao.getInvoiceByCustomer(account.getAccountId(), startDateCal.getTime(), futureDueEndDate);
			if (futureDueInvoiceList != null && futureDueInvoiceList.size() > 0) {
				for (Invoice invoice : futureDueInvoiceList) {
					double openBalance = getOpenBalanceForInvoice(invoice);					
					if (openBalance > 0) {
						futureDueAmount += openBalance;
						invoice.setBalance(openBalance);						
						requiredFutureDueInvoiceList.add(invoice);
					}
				}
			}
			model.addAttribute("futureDueInvoiceList", requiredFutureDueInvoiceList);
			//End future due invoice process

			System.out.println("after  requiredPastInvoiceList payNow"
					+ requiredPastInvoiceList);

			model.addAttribute("requiredPastInvoiceList",
					requiredPastInvoiceList);
			double currentPastDueAmount = 0;
			currentPastDueAmount=pastDueAmount+dueAmount;
			// session.setAttribute("currentPastDueAmount",currentPastDueAmount);
			model.addAttribute("dueAmount",dueAmount);
			model.addAttribute("pastDueAmount",pastDueAmount);
			model.addAttribute("currentPastDueAmount",currentPastDueAmount);
			model.addAttribute("futureDueAmount",futureDueAmount);			
			//END PAST INVOICE & DUE 
			// Invoice end

		} else {
			model.addAttribute("errMsg", "Please Login");
			// //System.out.println(" else ModelAndView(   requiredPastInvoiceList payNow");
			return new ModelAndView("login", model.asMap());
		}
		// //System.out.println("ModelAndView(   requiredPastInvoiceList payNow");
		return new ModelAndView("payNow", model.asMap());
	}

	//

	@RequestMapping(value = "PayForSignupInvoice", method = RequestMethod.GET)
	public ModelAndView payForSignupInvoice(
			@RequestParam("signupId") String signupId,

			final HttpServletRequest request, final HttpServletResponse response) {

		Model model = new ExtendedModelMap();
		// check if user is logged in
		Authentication a = SecurityContextHolder.getContext()
				.getAuthentication();
		String userId = null;

		try {
			userId = ((UserDetails) a.getPrincipal()).getUsername();
		} catch (Exception e) {
			// model.addAttribute("errMsg", "Your session is expired");
			// return new ModelAndView("login", model.asMap());
		}

		Account account = null;
		User user = null;
		List<PaymentMethod> paymentMethodList = null;
		if (userId != null && !"".equals(userId)) {
			account = accountDao.getByEmail(userId);
			user = getUserByAccount(account, user);
			paymentMethodList = paymentMethodDao
					.getPaymentMethodListForAccountID(account.getAccountId());
		}

		if (account != null) {
			model.addAttribute("accInfo", account);
			model.addAttribute("usInfo", user);

			int userCount = account.getUser().size();
			List<User> userS = new ArrayList<User>();
			int countmembers = 0;
			if (userCount > 1) {
				for (User u : account.getUser()) {
					if (!user.getUsername().equalsIgnoreCase(u.getUsername())
							&& u.isActive()) {
						countmembers = countmembers + 1;
						userS.add(u);
					}
				}
			}
			model.addAttribute("userCount", countmembers);
			model.addAttribute("userS", userS);
			model.addAttribute("AlluserS", account.getUser());

			model.addAttribute("paymentInfoLst", paymentMethodList);
			model.addAttribute("gottocontact", "false");

			// System.out.println("request.getSession.getAttribute-111-  ");
			// System.out.println("request.getSession.getAttribute--  " +
			// request.getSession());
			List<Invoice> invoiceList = null;
			double dueAmount = 0;
			invoiceList = invoiceDao.getInvoiceListForSignup(Long
					.valueOf(signupId));
			if (invoiceList != null && invoiceList.size() > 0) {
				for (Invoice invoice : invoiceList) {

					double openBalance = getOpenBalanceForInvoice(invoice);
					// System.out.println(" openBalance ::  "+openBalance);
					if (openBalance > 0) {

						dueAmount += openBalance;
						invoice.setBalance(openBalance);
						// requiredInvoiceList.add(invoice);
					}

				}
			}

			// Object s=request.getSession().getAttribute("pastDueAmount");
			model.addAttribute("pastDueAmount", dueAmount);
			// System.out.println(" after request.getSession.getAttribute--  ");

		}/*
		 * else { model.addAttribute("errMsg", "Please Login"); return new
		 * ModelAndView("login", model.asMap());
		 * 
		 * }
		 */
		return new ModelAndView("payPastDue", model.asMap());

		//

		// return new ModelAndView("", model.asMap());
	}

	@RequestMapping(value = "payPastDue", method = RequestMethod.GET)
	public ModelAndView trialPassShowForm(final HttpServletRequest request,
			final HttpServletResponse response) {

		Model model = new ExtendedModelMap();
		//

		// check if user is logged in
		Authentication a = SecurityContextHolder.getContext()
				.getAuthentication();
		String userId = null;

		try {
			userId = ((UserDetails) a.getPrincipal()).getUsername();
		} catch (Exception e) {
			// model.addAttribute("errMsg", "Your session is expired");
			// return new ModelAndView("login", model.asMap());
		}

		Account account = null;
		User user = null;
		List<PaymentMethod> paymentMethodList = null;
		if (userId != null && !"".equals(userId)) {
			account = accountDao.getByEmail(userId);
			// request.setAttribute("userId", userId);
			user = getUserByAccount(account, user);
			// paymentList = account.getPaymentMethod();
			paymentMethodList = paymentMethodDao
					.getPaymentMethodListForAccountID(account.getAccountId());
		}

		if (account != null) {
			model.addAttribute("accInfo", account);
			model.addAttribute("usInfo", user);

			int userCount = account.getUser().size();
			List<User> userS = new ArrayList<User>();
			int countmembers = 0;
			/*if (userCount > 1) {
				for (User u : account.getUser()) {
					if (!user.getUsername().equalsIgnoreCase(u.getUsername())
							&& u.isActive()) {
						countmembers = countmembers + 1;
						userS.add(u);
					}
				}
			}*/
			model.addAttribute("userCount", countmembers);
			model.addAttribute("userS", userS);
			model.addAttribute("AlluserS", account.getUser());

			model.addAttribute("paymentInfoLst", paymentMethodList);
			model.addAttribute("gottocontact", "false");

			// System.out.println("request.getSession.getAttribute-111-  ");
			// System.out.println("request.getSession.getAttribute--  " +
			// request.getSession());
		//	Object s = request.getSession().getAttribute("pastDueAmount");
			//model.addAttribute("pastDueAmount", s);
			// System.out.println(" after request.getSession.getAttribute--  ");

		}/*
		 * else { model.addAttribute("errMsg", "Please Login"); return new
		 * ModelAndView("login", model.asMap());
		 * 
		 * }
		 */
		return new ModelAndView("payPastDue", model.asMap());

		//

		// return new ModelAndView("", model.asMap());
	}

	@RequestMapping(value = "payCurrentPastDue", method = RequestMethod.GET)
	public ModelAndView payCurrentPastDue(final HttpServletRequest request,	final HttpServletResponse response) {

		Model model = new ExtendedModelMap();
		//

		// check if user is logged in
		Authentication a = SecurityContextHolder.getContext()
				.getAuthentication();
		String userId = null;

		try {
			userId = ((UserDetails) a.getPrincipal()).getUsername();
		} catch (Exception e) {
			// model.addAttribute("errMsg", "Your session is expired");
			// return new ModelAndView("login", model.asMap());
		}

		Account account = null;
		User user = null;
		List<PaymentMethod> paymentMethodList = null;
		if (userId != null && !"".equals(userId)) {
			account = accountDao.getByEmail(userId);
			// request.setAttribute("userId", userId);
			user = getUserByAccount(account, user);
			// paymentList = account.getPaymentMethod();
			paymentMethodList = paymentMethodDao
					.getPaymentMethodListForAccountID(account.getAccountId());
		}

		if (account != null) {
			model.addAttribute("accInfo", account);
			model.addAttribute("usInfo", user);

			int userCount = account.getUser().size();
			List<User> userS = new ArrayList<User>();
			int countmembers = 0;
			/*if (userCount > 1) {
				for (User u : account.getUser()) {
					if (!user.getUsername().equalsIgnoreCase(u.getUsername())
							&& u.isActive()) {
						countmembers = countmembers + 1;
						userS.add(u);
					}
				}
			}*/
			model.addAttribute("userCount", countmembers);
			model.addAttribute("userS", userS);
			model.addAttribute("AlluserS", account.getUser());

			model.addAttribute("paymentInfoLst", paymentMethodList);
			model.addAttribute("gottocontact", "false");
			
			String invoiceData = request.getParameter("invoiceArr");
			List<Invoice> invoiceLst = new ArrayList<Invoice>();
			if(StringUtils.isNotBlank(invoiceData)){
				String[] invoiceArr = invoiceData.split(",");				
				for(int i =0; i<invoiceArr.length; i++){
					if(StringUtils.isNotBlank(invoiceArr[i])){
						Invoice invoice = invoiceDao.getOne(Long.parseLong(invoiceArr[i]));
						double openBalance = getOpenBalanceForInvoice(invoice);
						if (openBalance > 0) {							
							invoice.setBalance(openBalance);
							invoiceLst.add(invoice);
						}						
					}					
				}
			}		
			model.addAttribute("invoiceLst", invoiceLst);			
			// System.out.println("request.getSession.getAttribute-111-  ");
			// System.out.println("request.getSession.getAttribute--  " +
			// request.getSession());
		//	Object s = request.getSession()
			//		.getAttribute("currentPastDueAmount");
		//	Object s2 = request.getSession().getAttribute("pastdueamount");
			//s2 = "";
			// request.getSession().setAttribute("pastdueamount","");
			//model.addAttribute("currentPastDueAmount", s);
			//model.addAttribute("pastdueamount", s2);
			// System.out.println(" after request.getSession.getAttribute--  ");

		}/*
		 * else { model.addAttribute("errMsg", "Please Login"); return new
		 * ModelAndView("login", model.asMap());
		 * 
		 * }
		 */
		model.addAttribute("account", new Account());
		return new ModelAndView("payPastDue", model.asMap());

		//

		// return new ModelAndView("", model.asMap());
	}

	@RequestMapping(value = "/insertPaymentDetailsForAchTransaction", method = RequestMethod.POST)
	public void insertPaymentDetailsForAchTransaction(
			@RequestParam("bankAccountNumber") String bankAccountNumber,
			@RequestParam("bankRoutingNumber") String bankRoutingNumber,
			@RequestParam("checkNumber") String checkNumber,
			@RequestParam("cardName") String cardName,
			@RequestParam("totalAmt") String totalAmt,
			@RequestParam("transId") String transId,
			@RequestParam("responseText") String responseText,
			@RequestParam("actCode") String actCode,
			@RequestParam("apprCode") String apprCode,
			@RequestParam("billingAddress1") String billingAddress1,
			@RequestParam("billingAddress2") String billingAddress2,
			@RequestParam("billingCity") String billingCity,
			@RequestParam("billingState") String billingState,
			@RequestParam("billingZip") String billingZip,
			HttpServletRequest request, final HttpServletResponse response) {
		// System.out.println("in ProcessDirectPayment >>");
		try {
			int amtToJetPay = (int) (Float.parseFloat(totalAmt) * 100);
			JetPayPayment jetPayPayment = new JetPayPayment();
			jetPayPayment.setTransId(transId);
			jetPayPayment.setOrderNumber(transId);
			jetPayPayment.setBankAccountNumber(bankAccountNumber);
			jetPayPayment.setResponseText(responseText);
			jetPayPayment.setBankRoutingNumber(bankRoutingNumber);
			jetPayPayment.setName(cardName);
			jetPayPayment.setCheckNumber(checkNumber);
			jetPayPayment.setActCode(actCode);
			jetPayPayment.setApprCode(apprCode);
			if (totalAmt != null && !"".equals(totalAmt.trim())) {
				jetPayPayment.setAmount(Double.valueOf(amtToJetPay));
			} else {
				jetPayPayment.setAmount(0D);
			}
			jetPayPayment.setBillingAddress1(billingAddress1);
			jetPayPayment.setBillingAddress2(billingAddress2);
			jetPayPayment.setBillingCity(billingCity);
			jetPayPayment.setBillingState(billingState);
			jetPayPayment.setBillingZip(billingZip);
			jetPayPayment.setBillingCountry("USA");
			jetPayPayment.setPaymentType(Constants.PAYMENT_ACH);

			jetPayPaymentDao.save(jetPayPayment);

		} catch (Exception se) {
			// System.out.println("Error occoured");
			// System.out.println(se.getLocalizedMessage());
			// System.out.println(se.getMessage());
			// System.out.println(se.getCause());
			log.error("Error  ", se);
		}
	}

	@RequestMapping(value = "/getPaymentMethodByToken", method = RequestMethod.GET)
	public @ResponseBody
	String getPaymentMethodByToken(@RequestParam(value = "token") String token,
			HttpServletRequest request, final HttpServletResponse response) {
		JSONObject json = new JSONObject();
		try {
			PaymentMethod paymentMethod = paymentMethodDao
					.getPaymentMethodByTokenNumber(token);
			if (paymentMethod != null) {
				json.put("cardNumber", paymentMethod.getCardNumber());
				json.put("expirationMonth", paymentMethod.getExpirationMonth());
				json.put("expirationYear", paymentMethod.getExpirationYear());
				json.put("billingAddressLine1",
						paymentMethod.getBillingAddressLine1());
				json.put("billingAddressLine2",
						paymentMethod.getBillingAddressLine2());
				json.put("billingCity", paymentMethod.getBillingCity());
				json.put("billingCountry", paymentMethod.getBillingCountry());
				json.put("billingState", paymentMethod.getBillingState());
				json.put("billingZip", paymentMethod.getBillingZip());
				json.put("fullName", paymentMethod.getFullName());
				json.put("orderNumber", paymentMethod.getOrderNumber());
				json.put("securityCode", paymentMethod.getSecurityCode());
				json.put("nickName", paymentMethod.getNickName());
				json.put("portalStatus", paymentMethod.getPortalStatus());
			}
		} catch (Exception se) {
			// System.out.println("Error occoured");
			log.error("Error  ", se);
		}
		return json.toString();
	}

	@RequestMapping(value = "/checkPaymentMethodStatus", method = RequestMethod.POST)
	public @ResponseBody
	String checkPaymentMethodStatus(
			@RequestParam(value = "orderNumber") String orderNumber,
			final HttpServletRequest request, final HttpServletResponse response) {
		StringBuffer resp = new StringBuffer();
		try {
			long endTimeMillis = System.currentTimeMillis() + 10000;
			while (true) {
				if (System.currentTimeMillis() < endTimeMillis) {

					JetPayPayment jetPayPayment = jetPayPaymentDao
							.getByOrderNumber(orderNumber);

					if (jetPayPayment != null
							&& jetPayPayment.getActCode() != null
							&& !jetPayPayment.getActCode().equals("")) {

						if (jetPayPayment.getActCode().equals(
								Constants.PAYMENT_ACTION_CODE_SUCCESS)) {
							resp.append("SUCCESS__Token number: "
									+ jetPayPayment.getCcToken());
						} else {
							resp.append("FAILURE__Action Code: "
									+ jetPayPayment.getActCode()
									+ " Error Message: "
									+ jetPayPayment.getResponseText());
						}
						break;
					}

					// check status
					Thread.sleep(2000);
				} else {
					resp.append("FAILURE__");
					break;
				}
			}
		} catch (Exception se) {
			log.error("Error  ", se);
			resp.append("FAILURE__");
		}
		return resp.toString();
	}

	@RequestMapping(value = "/checkPaymentProcessStatus", method = RequestMethod.POST)
	public @ResponseBody
	String checkPaymentProcessStatus(
			@RequestParam(value = "orderNumber") String orderNumber,
			final HttpServletRequest request, final HttpServletResponse response) {
		StringBuffer resp = new StringBuffer();
		try {
			long endTimeMillis = System.currentTimeMillis() + 10000;
			while (true) {
				if (System.currentTimeMillis() < endTimeMillis) {
					// JetPayPayment jetPayPayment =
					// jetPayPaymentDao.getByOrderNumber("901493230249");
					JetPayPayment jetPayPayment = jetPayPaymentDao
							.getByOrderNumber(orderNumber);
					if (jetPayPayment != null
							&& jetPayPayment.getActCode() != null
							&& !jetPayPayment.getActCode().equals("")) {

						if (jetPayPayment.getActCode().equals(
								Constants.PAYMENT_ACTION_CODE_SUCCESS)) {
							PaymentMethod paymentMethod = paymentMethodDao
									.getByOrderNumber(orderNumber);
							if (paymentMethod != null
									&& paymentMethod.getId() != null
									&& paymentMethod.getId() > 0) {
								resp.append("SUCCESS__"
										+ jetPayPayment.getCcToken() + "__"
										+ paymentMethod.getId());
							} else {
								resp.append("SUCCESS__"
										+ jetPayPayment.getCcToken() + "__0");
							}
						} else {
							resp.append("FAILURE__Action Code: "
									+ jetPayPayment.getActCode()
									+ " Error Message: "
									+ jetPayPayment.getResponseText());
						}
						break;
					}

					// check status
					Thread.sleep(2000);
				} else {
					resp.append("FAILURE__");
					break;
				}
			}
		} catch (Exception se) {
			log.error("Error  ", se);
			resp.append("FAILURE__");
		}
		return resp.toString();
	}

	@RequestMapping(value = "/updateCardPaymentMethodByToken", method = RequestMethod.POST)
	public @ResponseBody
	String updateCardPaymentMethodByToken(@RequestParam("token") String token,
			@RequestParam("cardExpMonth") String cardExpMonth,
			@RequestParam("cardExpYear") String cardExpYear,
			@RequestParam("nickName") String nickName,
			/*
			 * @RequestParam("addressLine1") String addressLine1,
			 * 
			 * @RequestParam("addressLine2") String addressLine2,
			 * 
			 * @RequestParam("billingCity") String billingCity,
			 * 
			 * @RequestParam("billingState") String billingState,
			 * 
			 * @RequestParam("billingZip") String billingZip,
			 */
			final HttpServletRequest request, final HttpServletResponse response) {

		StringBuffer resp = new StringBuffer();
		HttpPost post = new HttpPost(Constants.JETPAY_XML_SCHEMA_URL);
		JetPayPayment jetPayPayment = new JetPayPayment();
		StringBuffer errMsg = new StringBuffer();
		try {
			// Prepare HTTP post
			StringBuffer inputXMLStrBuffer = new StringBuffer("<JetPay>");
			inputXMLStrBuffer.append("<TransactionID>"
					+ RandomStringUtils.randomAlphanumeric(18)
					+ "</TransactionID>");
			inputXMLStrBuffer
					.append("<TransactionType>"
							+ JetpayTransactionTypeEnum.TOKENIZE
							+ "</TransactionType>");
			inputXMLStrBuffer.append("<TerminalID>"
					+ Constants.JETPAY_TERMINAL_ID + "</TerminalID>");
			inputXMLStrBuffer.append("<CardExpMonth>" + cardExpMonth
					+ "</CardExpMonth>");
			inputXMLStrBuffer.append("<CardExpYear>" + cardExpYear
					+ "</CardExpYear>");
			inputXMLStrBuffer.append("<Origin>" + "INTERNET" + "</Origin>");
			inputXMLStrBuffer.append("<Token Tokenize='true'>" + token
					+ "</Token>");
			inputXMLStrBuffer.append("</JetPay>");
			post.setEntity(new StringEntity(inputXMLStrBuffer.toString()));
			post.addHeader("Content-type", "text/xml; charset=ISO-8859-1");

			HttpClient httpclient = HttpClients.createDefault();
			HttpResponse response2 = httpclient.execute(post);
			String xml = EntityUtils.toString(response2.getEntity());

			// parse response
			if (xml != null) {
				InputSource source = new InputSource(new StringReader(xml));
				DocumentBuilderFactory dbf = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				Document document = db.parse(source);
				XPathFactory xpathFactory = XPathFactory.newInstance();
				XPath xpath = xpathFactory.newXPath();

				String actionCode = xpath.evaluate(
						"/JetPayResponse/ActionCode", document);
				if (actionCode != null && !actionCode.equals("")) {
					jetPayPayment.setActCode(actionCode);
					jetPayPayment.setTransId(xpath.evaluate(
							"/JetPayResponse/TransactionID", document));
					jetPayPayment.setResponseText(xpath.evaluate(
							"/JetPayResponse/ResponseText", document));
					jetPayPayment.setCcToken(xpath.evaluate(
							"/JetPayResponse/Token", document));

					if (actionCode
							.equals(Constants.PAYMENT_ACTION_CODE_SUCCESS)
							&& (jetPayPayment.getCcToken() != null && !jetPayPayment
									.getCcToken().equals(""))) {
						jetPayPayment.setApprCode(xpath.evaluate(
								"/JetPayResponse/Approval", document));

						// get old payment method
						PaymentMethod oldPaymentMethod = new PaymentMethod();
						oldPaymentMethod = paymentMethodDao
								.getPaymentMethodByTokenNumber(token);

						// Inactivate existing token
						oldPaymentMethod
								.setPortalStatus(PortalStatusEnum.INACTIVE
										.toString());
						oldPaymentMethod.setLastUpdated(Calendar.getInstance());
						paymentMethodDao.save(oldPaymentMethod);

						// persist new payment method
						PaymentMethod paymentMethod = new PaymentMethod();
						paymentMethod
								.setPaymentMethodType(PaymentMethodTypeEnum.CREDIT
										.toString());
						paymentMethod.setPortalStatus(PortalStatusEnum.ACTIVE
								.toString());
						paymentMethod
								.setTokenNumber(jetPayPayment.getCcToken());
						paymentMethod.setTransId(jetPayPayment.getTransId());
						paymentMethod.setCustomer(oldPaymentMethod
								.getCustomer());
						paymentMethod.setFullName(oldPaymentMethod
								.getFullName());
						paymentMethod.setCardType(oldPaymentMethod
								.getCardType());
						paymentMethod.setCardNumber(oldPaymentMethod
								.getCardNumber());
						paymentMethod.setNickName(nickName);
						paymentMethod.setExpirationMonth(cardExpMonth);
						paymentMethod.setExpirationYear(cardExpYear);
						paymentMethod.setBillingAddressLine1(oldPaymentMethod
								.getBillingAddressLine1());
						paymentMethod.setBillingAddressLine2(oldPaymentMethod
								.getBillingAddressLine2());
						paymentMethod.setBillingCity(oldPaymentMethod
								.getBillingCity());
						paymentMethod.setBillingState(oldPaymentMethod
								.getBillingState());
						paymentMethod.setBillingZip(oldPaymentMethod
								.getBillingZip());
						paymentMethod.setBillingCountry(oldPaymentMethod
								.getBillingCountry());
						paymentMethod.setLastUpdated(Calendar.getInstance());
						paymentMethodDao.save(paymentMethod);
					} else {
						errMsg.append("Action Code: "
								+ jetPayPayment.getActCode()
								+ " Response Text: "
								+ jetPayPayment.getResponseText()
								+ " ErrMsg: "
								+ xpath.evaluate("/JetPayResponse/ErrMsg",
										document));
					}
				}

				if (jetPayPayment.getCcToken() != null
						&& !jetPayPayment.getCcToken().equals("")) {
					resp.append("SUCCESS__Token Number: "
							+ jetPayPayment.getCcToken());
				} else {
					resp.append("FAILURE__Action Code: "
							+ jetPayPayment.getActCode() + " Error Message: "
							+ jetPayPayment.getResponseText());
				}

			} else {
				resp.append("FAILURE__");
				errMsg.append("Null response received.");
			}
		} catch (Exception e) {
			resp.append("FAILURE__");
			log.error("Error  ", e);
			errMsg.append("Exception: " + e.toString());
		} finally {
			jetPayPayment.setErrMsg(errMsg.toString());
			jetPayPaymentDao.save(jetPayPayment);
			post.releaseConnection();
		}
		return resp.toString();
	}

	@RequestMapping(value = "/processCardPaymentByToken", method = RequestMethod.POST)
	public @ResponseBody
	JetPayPayment processCardPaymentByToken(
			@RequestParam("token") String token,
			@RequestParam("amount") String amount,
			final HttpServletRequest request, final HttpServletResponse response)
			throws Exception {
		// Prepare HTTP post
		HttpPost post = new HttpPost(Constants.JETPAY_XML_SCHEMA_URL);
		StringBuffer inputXMLStrBuffer = new StringBuffer("<JetPay>");
		inputXMLStrBuffer
				.append("<TransactionID>"
						+ RandomStringUtils.randomAlphanumeric(18)
						+ "</TransactionID>");
		inputXMLStrBuffer.append("<TransactionType>"
				+ JetpayTransactionTypeEnum.SALE + "</TransactionType>");
		inputXMLStrBuffer.append("<Token>" + token + "</Token>");
		inputXMLStrBuffer.append("<TotalAmount>" + amount + "</TotalAmount>");
		inputXMLStrBuffer.append("<TerminalID>" + Constants.JETPAY_TERMINAL_ID
				+ "</TerminalID>");
		inputXMLStrBuffer.append("</JetPay>");

		post.setEntity(new StringEntity(inputXMLStrBuffer.toString()));
		post.addHeader("Content-type", "text/xml; charset=ISO-8859-1");
		HttpClient httpclient = HttpClients.createDefault();
		JetPayPayment jetPayPayment = new JetPayPayment();
		try {
			HttpClient httpclient2 = HttpClients.createDefault();
			HttpResponse response2 = httpclient2.execute(post);
			String xml = EntityUtils.toString(response2.getEntity());
			if (xml != null) {

				InputSource source = new InputSource(new StringReader(xml));

				DocumentBuilderFactory dbf = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				Document document = db.parse(source);

				XPathFactory xpathFactory = XPathFactory.newInstance();
				XPath xpath = xpathFactory.newXPath();

				jetPayPayment.setTransId(xpath.evaluate(
						"/JetPayResponse/TransactionID", document));
				jetPayPayment.setActCode(xpath.evaluate(
						"/JetPayResponse/ActionCode", document));
				jetPayPayment.setResponseText(xpath.evaluate(
						"/JetPayResponse/ResponseText", document));
			}
		} catch (Exception e) {
			log.error("Error  ", e);
		} finally {
			post.releaseConnection();
		}
		return jetPayPayment;
	}

	//
	/*
	 * @RequestMapping(value="/processCardPaymentAndTokennizeCard",
	 * method=RequestMethod.POST) public @ResponseBody JetPayPayment
	 * processCardPaymentAndTokennizeCard(
	 * 
	 * @RequestParam("cardNum") String cardNum,
	 * 
	 * @RequestParam("cardExpMonth") String cardExpMonth,
	 * 
	 * @RequestParam("cardExpYear") String cardExpYear,
	 * 
	 * @RequestParam("amount") String amount,
	 * 
	 * @RequestParam("billingAddress") String billingAddress,
	 * 
	 * @RequestParam("billingCity") String billingCity,
	 * 
	 * @RequestParam("billingStateProv") String billingStateProv,
	 * 
	 * @RequestParam("billingPostalCode") String billingPostalCode,
	 * 
	 * @RequestParam("billingCountry") String billingCountry, final
	 * HttpServletRequest request, final HttpServletResponse response) {
	 * 
	 * // Prepare HTTP post PostMethod post = new
	 * PostMethod(Constants.JETPAY_XML_SCHEMA_URL); StringBuffer
	 * inputXMLStrBuffer = new StringBuffer("<JetPay>");
	 * inputXMLStrBuffer.append
	 * ("<TransactionID>"+RandomStringUtils.randomAlphanumeric
	 * (18)+"</TransactionID>");
	 * inputXMLStrBuffer.append("<TransactionType>"+JetpayTransactionTypeEnum
	 * .SALE+"</TransactionType>");
	 * inputXMLStrBuffer.append("<CardNum Tokenize='true'>"
	 * +cardNum+"</CardNum>");
	 * inputXMLStrBuffer.append("<CardExpMonth>"+token+"</CardExpMonth>");
	 * inputXMLStrBuffer.append("<CardExpYear>"+token+"</CardExpYear>");
	 * inputXMLStrBuffer.append("<BillingAddress>"+token+"</BillingAddress>");
	 * inputXMLStrBuffer.append("<BillingCity>"+token+"</BillingCity>");
	 * inputXMLStrBuffer
	 * .append("<TokeBillingStateProvn>"+token+"</BillingStateProv>");
	 * inputXMLStrBuffer
	 * .append("<BillingPostalCode>"+token+"</BillingPostalCode>");
	 * inputXMLStrBuffer.append("<BillingCountry>"+token+"</BillingCountry>");
	 * 
	 * inputXMLStrBuffer.append("<Token>"+token+"</Token>");
	 * inputXMLStrBuffer.append("<TotalAmount>"+amount+"</TotalAmount>");
	 * inputXMLStrBuffer
	 * .append("<TerminalID>"+Constants.JETPAY_TERMINAL_ID+"</TerminalID>");
	 * inputXMLStrBuffer.append("</JetPay>");
	 * 
	 * post.setRequestEntity(new InputStreamRequestEntity(new
	 * StringBufferInputStream(inputXMLStrBuffer.toString())));
	 * post.setRequestHeader("Content-type", "text/xml; charset=ISO-8859-1");
	 * HttpClient httpclient = new HttpClient(); JetPayPayment jetPayPayment =
	 * new JetPayPayment(); try { httpclient.executeMethod(post); String xml =
	 * post.getResponseBodyAsString(); if(xml != null){
	 * 
	 * InputSource source = new InputSource(new StringReader(xml));
	 * 
	 * DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	 * DocumentBuilder db = dbf.newDocumentBuilder(); Document document =
	 * db.parse(source);
	 * 
	 * XPathFactory xpathFactory = XPathFactory.newInstance(); XPath xpath =
	 * xpathFactory.newXPath();
	 * 
	 * jetPayPayment.setTransId(xpath.evaluate("/JetPayResponse/TransactionID",
	 * document));
	 * jetPayPayment.setActCode(xpath.evaluate("/JetPayResponse/ActionCode",
	 * document));
	 * jetPayPayment.setResponseText(xpath.evaluate("/JetPayResponse/ResponseText"
	 * , document)); } } catch(Exception e){ log.error("Error  ",e);
	 * 
	 * } finally { post.releaseConnection(); } return jetPayPayment; }
	 */

	@RequestMapping(value = "/addBankInfoByTokenize", method = RequestMethod.POST)
	public @ResponseBody
	String addBankInfoByTokenize(@RequestParam("accountID") String accountID,
			@RequestParam("accountType") String accountType,
			@RequestParam("accountName") String accountName,
			@RequestParam("accountNumber") String accountNumber,
			@RequestParam("routingNumber") String routingNumber,
			@RequestParam("checkNumber") String checkNumber,
			@RequestParam("nickName") String nickName,
			final HttpServletRequest request, final HttpServletResponse response) {
		StringBuffer resp = new StringBuffer();
		StringBuffer errMsg = new StringBuffer();
		HttpPost post = new HttpPost(Constants.JETPAY_XML_SCHEMA_URL);
		JetPayPayment jetPayPayment = new JetPayPayment();
		// Prepare HTTP post
		StringBuffer inputXMLStrBuffer = new StringBuffer();
		inputXMLStrBuffer.append("<JetPay>");
		inputXMLStrBuffer
				.append("<TransactionID>"
						+ RandomStringUtils.randomAlphanumeric(18)
						+ "</TransactionID>");
		inputXMLStrBuffer.append("<TransactionType>"
				+ JetpayTransactionTypeEnum.TOKENIZE + "</TransactionType>");
		inputXMLStrBuffer.append("<TerminalID>" + Constants.JETPAY_TERMINAL_ID
				+ "</TerminalID>");
		inputXMLStrBuffer.append("<CardName>" + accountName + "</CardName>");
		inputXMLStrBuffer.append("<ACH Type=\""
				+ ACH_AccountTypeEnum.valueOf(accountType) + "\">");
		inputXMLStrBuffer.append("<AccountNumber>" + accountNumber
				+ "</AccountNumber>");
		inputXMLStrBuffer.append("<ABA>" + routingNumber + "</ABA>");
		inputXMLStrBuffer.append("<CheckNumber>" + checkNumber
				+ "</CheckNumber>");
		inputXMLStrBuffer.append("</ACH>");
		inputXMLStrBuffer.append("</JetPay>");

		try {
			post.setEntity(new StringEntity(inputXMLStrBuffer.toString()));
			post.addHeader("Content-type", "text/xml; charset=ISO-8859-1");
			HttpClient httpclient = HttpClients.createDefault();

			HttpResponse response2 = httpclient.execute(post);
			String xml = EntityUtils.toString(response2.getEntity());

			// jetPayPayment.setOldToken(xml);
			// jetPayPayment.setCustomerEmail(inputXMLStrBuffer.toString());

			// parse response
			if (xml != null) {
				InputSource source = new InputSource(new StringReader(xml));
				DocumentBuilderFactory dbf = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				Document document = db.parse(source);
				XPathFactory xpathFactory = XPathFactory.newInstance();
				XPath xpath = xpathFactory.newXPath();

				String actionCode = xpath.evaluate(
						"/JetPayResponse/ActionCode", document);
				if (actionCode != null && !actionCode.equals("")) {
					jetPayPayment.setActCode(actionCode);
					jetPayPayment.setTransId(xpath.evaluate(
							"/JetPayResponse/TransactionID", document));
					jetPayPayment.setResponseText(xpath.evaluate(
							"/JetPayResponse/ResponseText", document));
					jetPayPayment.setCcToken(xpath.evaluate(
							"/JetPayResponse/Token", document));

					if (actionCode
							.equals(Constants.PAYMENT_ACTION_CODE_SUCCESS)
							&& (jetPayPayment.getCcToken() != null && !jetPayPayment
									.getCcToken().equals(""))) {
						jetPayPayment.setApprCode(xpath.evaluate(
								"/JetPayResponse/Approval", document));

						// persist new payment method
						PaymentMethod paymentMethod = new PaymentMethod();
						paymentMethod.setPaymentMethodType(PaymentMethodTypeEnum.ACH
								.toString());
						paymentMethod.setPortalStatus(PortalStatusEnum.VP
								.toString());
						paymentMethod
								.setTokenNumber(jetPayPayment.getCcToken());
						paymentMethod.setTransId(jetPayPayment.getTransId());
						paymentMethod.setBankAccountType(ACH_AccountTypeEnum.valueOf(accountType).toString());
						paymentMethod.setNickName(nickName);
						paymentMethod.setCustomer(accountDao
								.findByAccountId(Long.valueOf(accountID)));
						paymentMethod.setFullName(accountName);
						paymentMethod.setCheckNumber(checkNumber);

						if (accountNumber.length() > 4) {
							paymentMethod
									.setCardNumber(Constants.CARD_MASKED_FIRST_DIGITS
											+ accountNumber
													.substring(accountNumber
															.length() - 4));
						} else {
							paymentMethod
									.setCardNumber(Constants.CARD_MASKED_FIRST_DIGITS
											+ accountNumber);
						}
						paymentMethod.setLastUpdated(Calendar.getInstance());
						paymentMethodDao.save(paymentMethod);
					} else {
						errMsg.append("Action Code: "
								+ jetPayPayment.getActCode()
								+ " Response Text: "
								+ jetPayPayment.getResponseText()
								+ " ErrMsg: "
								+ xpath.evaluate("/JetPayResponse/ErrMsg",
										document));
					}
				}

				if (jetPayPayment.getCcToken() != null
						&& !jetPayPayment.getCcToken().equals("")) {
					resp.append("SUCCESS__Token Number: "
							+ jetPayPayment.getCcToken());
				} else {
					resp.append("FAILURE__Action Code: "
							+ jetPayPayment.getActCode() + " Error Message: "
							+ jetPayPayment.getResponseText());
				}

			} else {
				resp.append("FAILURE__");
				errMsg.append("Null response received.");
			}
		} catch (Exception e) {
			resp.append("FAILURE__");
			log.error("Error  ", e);
			errMsg.append("Exception: " + e.toString());
		} finally {
			try {
				jetPayPayment.setErrMsg(errMsg.toString());
				jetPayPaymentDao.save(jetPayPayment);
				post.releaseConnection();
			} catch (Exception e1) {
				// System.out.println("Exception occured in finally..");
				log.error("Error  ", e1);
			}
		}
		return resp.toString();
	}

	// @RequestMapping(value = "/processEnquireRequestByTransactionId", method =
	// RequestMethod.GET)
	/*
	 * public @ResponseBody JetPayPayment processEnquireRequestByTransactionId(
	 * 
	 * @RequestParam("transactionId") String transactionId, final
	 * HttpServletRequest request, final HttpServletResponse response) { //
	 * Prepare HTTP post PostMethod post = new
	 * PostMethod(Constants.JETPAY_XML_SCHEMA_URL); StringBuffer
	 * inputXMLStrBuffer = new StringBuffer("<JetPay>");
	 * inputXMLStrBuffer.append("<TransactionID>"+
	 * RandomStringUtils.randomAlphanumeric(18)+ "</TransactionID>");
	 * inputXMLStrBuffer.append("<TransactionType>"+
	 * JetpayTransactionTypeEnum.SALE + "</TransactionType>");
	 * inputXMLStrBuffer.append("<Token>" + token + "</Token>");
	 * inputXMLStrBuffer.append("<TotalAmount>" + amount + "</TotalAmount>");
	 * inputXMLStrBuffer.append("<TerminalID>" + Constants.JETPAY_TERMINAL_ID +
	 * "</TerminalID>"); inputXMLStrBuffer.append("</JetPay>");
	 * 
	 * post.setRequestEntity(new InputStreamRequestEntity( new
	 * StringBufferInputStream(inputXMLStrBuffer.toString())));
	 * post.setRequestHeader("Content-type", "text/xml; charset=ISO-8859-1");
	 * HttpClient httpclient = new HttpClient(); JetPayPayment jetPayPayment =
	 * new JetPayPayment(); try { int result = httpclient.executeMethod(post);
	 * String xml = post.getResponseBodyAsString(); if (xml != null) {
	 * 
	 * InputSource source = new InputSource(new StringReader(xml));
	 * 
	 * DocumentBuilderFactory dbf = DocumentBuilderFactory .newInstance();
	 * DocumentBuilder db = dbf.newDocumentBuilder(); Document document =
	 * db.parse(source);
	 * 
	 * XPathFactory xpathFactory = XPathFactory.newInstance(); XPath xpath =
	 * xpathFactory.newXPath();
	 * 
	 * jetPayPayment.setTransId(xpath.evaluate( "/JetPayResponse/TransactionID",
	 * document)); jetPayPayment.setActCode(xpath.evaluate(
	 * "/JetPayResponse/ActionCode", document));
	 * jetPayPayment.setResponseText(xpath.evaluate(
	 * "/JetPayResponse/ResponseText", document)); } } catch (Exception e) {
	 * log.error("Error  ",e); } finally { post.releaseConnection(); } return
	 * jetPayPayment; }
	 */

	/*
	 * @RequestMapping(value="/getPaymentMethodForPaymentId/{paymentId}",
	 * method=RequestMethod.GET) public @ResponseBody PaymentMethod
	 * getPaymentMethodForPaymentId(@PathVariable Long paymentId, final
	 * HttpServletRequest request, final HttpServletResponse response) { Model
	 * model = new ExtendedModelMap(); List<PaymentMethod> paymentMethod =
	 * paymentMethodDao.getPaymentMethodByPaymentId(paymentId); try {
	 * model.addAttribute("paymentMethod", new PaymentMethod());
	 * if(paymentMethod != null && !paymentMethod.isEmpty()){ PaymentMethod pm =
	 * paymentMethod.get(0); Account customer = new Account();
	 * customer.setAccountId(pm.getCustomer().getAccountId());
	 * pm.setCustomer(customer); return pm; }
	 * 
	 * } catch (Exception se) { //System.out.println("Error occoured");
	 * slog.error("Error  ",e); } return null; }
	 */

	@RequestMapping(value = "/removePaymentMethod", method = RequestMethod.POST)
	public @ResponseBody
	String removePaymentMethod(
			@RequestParam(value = "accountID") String accountID,
			@RequestParam(value = "token") String token,
			@RequestParam(value = "isDelete") String isDelete,
			final HttpServletRequest request, final HttpServletResponse response) {
		JSONArray json = new JSONArray();
		List<Signup> signUpList = new ArrayList<Signup>();
		try {
			// System.out.println(" token "+token);
			PaymentMethod paymentMethod = paymentMethodDao
					.getPaymentMethodByTokenNumber(token);

			// TODO: check for any recurring payment
			// System.out.println(" AccountId :: "+accountID+"  PaymentId :: "+paymentMethod.getPaymentId());
			Account account = accountDao.findByAccountId(Long
					.valueOf(accountID));
			List<Signup> signupList = signupDao.getByCustomerAndPaymentMethod(
					account, paymentMethod.getId());

			if (signupList != null && signupList.size() > 0) {
				// System.out.println(" # of Signups :: "+signupList.size());
				boolean addSignupToList = false;
				double totalOpenBalanceForSignUp = 0;
				for (Signup signup : signupList) {
					addSignupToList = false;
					totalOpenBalanceForSignUp = 0;
					List<Invoice> invoiceList = invoiceDao
							.getInvoiceListForSignup(signup.getSignupId());
					for (Invoice invoice : invoiceList) {
						double openBalance = getOpenBalanceForInvoice(invoice);
						// System.out.println(" openBalance ::  "+openBalance);
						if (openBalance > 0) {
							addSignupToList = true;
							totalOpenBalanceForSignUp += openBalance;
						}
					}

					if (addSignupToList) {
						// System.out.println(" Add signup to list signupid "+signup.getSignupId()+" with total due :: "+totalOpenBalanceForSignUp);
						signUpList.add(signup);
					}
				}
			}

			if (signUpList != null && signUpList.size() > 0) {
				// System.out.println("Cannnot Delete payment method you selected ............ ");
				for (Signup signup : signUpList) {
					JSONObject obj = new JSONObject();
					obj.put("SignupId", signup.getSignupId());
					obj.put("SignupName", signup.getSignUpName());
					obj.put("SignupDate", DateFormatUtils.format(
							signup.getSignupDate(), "MM/dd/yyyy"));
					json.add(obj);
				}
			} else {
				// System.out.println("Delete payment method ............ ");
				paymentMethod.setPortalStatus(PortalStatusEnum.INACTIVE
						.toString());
				if (isDelete != null && isDelete.equalsIgnoreCase("Y")) {
					paymentMethod.setLastUpdated(Calendar.getInstance());
					paymentMethodDao.save(paymentMethod);
				}
			}

		} catch (Exception se) {
			// System.out.println("Error occoured");
			log.error("Error  ", se);
		}
		return json.toString();
	}

	private double getOpenBalanceForInvoice(Invoice invoice) {
		double openBalanceOnInvoice = 0, trueInvoiceValue = 0, sumOfDebitPayment = 0, sumOfCreditMemoFAWaiver = 0, sumOfCreditMemoWaiver = 0;
		double sumOfCreditMemoWriteOff = 0, sumOfCreditMemoRefund = 0, sumOfNSF = 0, sumOfChargeBack = 0;

		List<Payment> payments = paymentDao.getPaymentListForInvoice(invoice
				.getInvoiceId());
		for (Payment payment : payments) {
			// //System.out.println(" Type : "+payment.getType()+
			// " TypeEnum : "+PaymentTypeEnum.valueOf(payment.getType())+" Amount : "+payment.getAmount());
			switch (PaymentTypeEnum.getEnumByString(payment.getType())) {

			case CreditMemoFAWaiver:
				sumOfCreditMemoFAWaiver += payment.getAmount();
				break;
			case CreditMemoWaiver:
				sumOfCreditMemoWaiver += payment.getAmount();
				break;
			case CreditMemoRefund:
				sumOfCreditMemoRefund += payment.getAmount();
				break;
			case CreditMemoWriteOff:
				sumOfCreditMemoWriteOff += payment.getAmount();
				break;
			case Debit:
				sumOfDebitPayment += payment.getAmount();
				break;
			case NSF:
				sumOfNSF += payment.getAmount();
				break;
			case ChargeBack:
				sumOfChargeBack += payment.getAmount();
				break;
			default:
				break;
			}
		}

		/*
		 * //System.out.println(" sumOfCreditMemoFAWaiver  "+sumOfCreditMemoFAWaiver
		 * );
		 * //System.out.println(" sumOfCreditMemoWaiver  "+sumOfCreditMemoWaiver
		 * );
		 * //System.out.println(" sumOfCreditMemoRefund  "+sumOfCreditMemoRefund
		 * );
		 * //System.out.println(" sumOfCreditMemoWriteOff  "+sumOfCreditMemoWriteOff
		 * ); //System.out.println(" sumOfDebitPayment  "+sumOfDebitPayment);
		 * //System.out.println(" sumOfNSF  "+sumOfNSF);
		 * //System.out.println(" sumOfChargeBack  "+sumOfChargeBack);
		 */

		trueInvoiceValue = invoice.getAmount()
				- (sumOfCreditMemoWaiver + sumOfCreditMemoFAWaiver + sumOfCreditMemoWriteOff);
		// //System.out.println(" trueInvoiceValue "+trueInvoiceValue);

		openBalanceOnInvoice = trueInvoiceValue
				- (sumOfDebitPayment + sumOfCreditMemoRefund)
				+ ((sumOfNSF > 0 ? sumOfNSF : sumOfChargeBack));
		// //System.out.println(" openBalanceOnInvoice "+openBalanceOnInvoice);

		return openBalanceOnInvoice;
	}

	@RequestMapping(value = "/isCardAlreadyExist", method = RequestMethod.GET)
	public @ResponseBody
	String isCardAlreadyExist(
			@RequestParam(value = "accountID") String accountID,
			@RequestParam(value = "cardNumber") String cardNumber,
			@RequestParam(value = "expMonth") String expMonth,
			@RequestParam(value = "expYear") String expYear,
			final HttpServletRequest request, final HttpServletResponse response) {
		JSONObject json = new JSONObject();
		try {
			String last4Digits = cardNumber.substring(cardNumber.length() - 4);
			cardNumber = Constants.CARD_MASKED_FIRST_DIGITS + last4Digits;
			Long paymentId = paymentMethodDao.isCardAlreadyExist(
					Long.valueOf(accountID), cardNumber, expMonth, expYear);
			if (paymentId != null && paymentId > 0) {
				json.put("paymentId", paymentId);
			} else {
				json.put("paymentId", 0);
			}
		} catch (Exception se) {
			// System.out.println("Error occoured");
			log.error("Error  ", se);
			json.put("paymentId", 1);
		}
		return json.toString();
	}

	@RequestMapping(value = "/isBankInfoAlreadyExist", method = RequestMethod.GET)
	public @ResponseBody
	String isBankInfoAlreadyExist(
			@RequestParam(value = "accountID") String accountID,
			@RequestParam(value = "bankAccountNumber") String bankAccountNumber,
			final HttpServletRequest request, final HttpServletResponse response) {
		JSONObject json = new JSONObject();
		try {
			String last4Digits = bankAccountNumber.substring(bankAccountNumber
					.length() - 4);
			bankAccountNumber = Constants.CARD_MASKED_FIRST_DIGITS
					+ last4Digits;
			Long paymentId = paymentMethodDao.isBankInfoAlreadyExist(
					Long.valueOf(accountID), bankAccountNumber);
			if (paymentId != null && paymentId > 0) {
				json.put("paymentId", paymentId);
			} else {
				json.put("paymentId", 0);
			}
		} catch (Exception se) {
			// System.out.println("Error occoured");
			log.error("Error  ", se);
			json.put("paymentId", 1);
		}
		return json.toString();
	}

	@RequestMapping(value = "/getPaymentMethodOptionsForAccount", method = RequestMethod.GET)
	public @ResponseBody
	String getPaymentMethodOptionsForAccount(
			@RequestParam(value = "accountID") String accountID,
			final HttpServletRequest request, final HttpServletResponse response) {
		JSONObject json = new JSONObject();
		try {
			Account account = accountDao.findByAccountId(Long
					.valueOf(accountID));
			List<PaymentMethod> paymentMethodList = paymentMethodDao
					.getPaymentMethodListForAccount(account);
			if (paymentMethodList != null && paymentMethodList.size() > 0) {
				for (PaymentMethod paymentMethod : paymentMethodList) {
					json.put(paymentMethod.getTokenNumber(),
							paymentMethod.getCardNumber());
				}
			} else {
				json.put("", "");
			}
		} catch (Exception se) {
			// System.out.println("Error occoured");
			log.error("Error  ", se);
		}
		return json.toString();
	}

	@RequestMapping(value = "/updatePaymentMethodForSignup", method = RequestMethod.POST)
	public @ResponseBody
	String updatePaymentMethodForSignup(
			@RequestParam(value = "token") String token,
			@RequestParam(value = "signupid") String signupid,
			@RequestParam(value = "accountID") String accountID,
			final HttpServletRequest request, final HttpServletResponse response) {
		JSONObject json = new JSONObject();
		try {

			// System.out.println(" token "+token);
			// System.out.println(" signupid "+signupid);
			// System.out.println(" accountID "+accountID);

			Account customer = accountDao.findByAccountId(Long
					.valueOf(accountID));
			PaymentMethod paymentMethod = paymentMethodDao
					.getPaymentMethodByTokenNumber(token);
			if (signupid.endsWith("_")) {

				if (signupid.charAt(signupid.length() - 1) == '_') {
					signupid = signupid.substring(0, signupid.lastIndexOf("_"));
				}

				String[] signupList = signupid.split("_");

				// System.out.println(" signupList.length "+signupList.length);

				if (signupList.length > 0) {

					for (int i = 0; i < signupList.length; i++) {
						String signupTemp = signupList[i];
						Signup signup = signupDao.findByCustomerAndSignupId(
								customer, Long.valueOf(signupTemp));
						signup.setPaymentMethod(paymentMethod);
						signup.setLastUpdated(Calendar.getInstance());
						signupDao.save(signup);
						// System.out.println("  if signup.getSignupId()  "+signup.getSignupId());
					}

				}
			} else {
				Signup signup = signupDao.findByCustomerAndSignupId(customer,
						Long.valueOf(signupid));
				signup.setPaymentMethod(paymentMethod);
				signup.setLastUpdated(Calendar.getInstance());
				signupDao.save(signup);
				// System.out.println("  else signup.getSignupId()  "+signup.getSignupId());
			}
			json.put("RESULT", "SUCCESS");
		} catch (Exception se) {
			// System.out.println("Error occoured");
			log.error("Error  ", se);
			json.put("RESULT", "FAILURE");
		}
		return json.toString();
	}
}
