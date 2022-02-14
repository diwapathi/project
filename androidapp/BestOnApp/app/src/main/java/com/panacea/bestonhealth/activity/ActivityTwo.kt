package com.panacea.bestonhealth.activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bestonhealth.Database.DatabaseQueryClass
import com.bestonhealth.R
import com.panacea.bestonhealth.adapter.AddressAdapter
import com.panacea.bestonhealth.adapter.SearchAdapter
import com.panacea.bestonhealth.commonUtil.CommonMethod
import com.panacea.bestonhealth.model.*
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import cz.msebera.android.httpclient.entity.StringEntity
import io.realm.Realm
import org.json.JSONObject
import java.util.*


@Suppress("DEPRECATION")
class ActivityTwo : AppCompatActivity() {


    private lateinit var realm: Realm
    lateinit var mtoolbar: Toolbar
    lateinit var mbt_signin: Button
    lateinit var mbt_register: Button
    var adapter: SearchAdapter?=null
    lateinit var met_houseno_floor: EditText
    lateinit var met_street: EditText
    lateinit var met_landmark: EditText
    lateinit var met_pincode: EditText
    lateinit var met_city: EditText
    lateinit var met_state: EditText
    lateinit var met_contactperson: EditText
    lateinit var met_mobile_number: EditText
    lateinit var mtvlogin: TextView
    lateinit var mll_checkoutaddress_button: LinearLayout
    lateinit var mll_login_button: FrameLayout
    private var prgDialog: ProgressDialog? = null
    var address = Address()
    val addressList = ArrayList<Address>()
    var productRealm = ArrayList<ProductRealm>()
    val databaseQueryClass = DatabaseQueryClass(this)
    lateinit var mcard_view_signin: CardView
    lateinit var mllcheckout_stepper: LinearLayout
    lateinit var mcard_view_address_data: CardView
    lateinit var mcheckoutaddress: RecyclerView
    lateinit var miv_addbtn: ImageView
    var adapterAddress: AddressAdapter?=null
    var currentToken=""
    var addressLine1=""
    var addressLine2=""
    var addressLine3=""
    var pinCode=""
    var city=""
    var state=""
    var mobileNumber=""
    var name=""
    var selected=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.addcheckout_address)
        setToolBar()
        // Open the realm for the UI thread.
        realm = Realm.getDefaultInstance()
        mcheckoutaddress = findViewById(R.id.checkoutaddress)
        miv_addbtn = findViewById(R.id.iv_addbtn)
        mbt_signin = findViewById(R.id.bt_signin)
        mbt_register = findViewById(R.id.bt_register)
        mtvlogin = findViewById(R.id.tvlogin)
        mcard_view_signin = findViewById(R.id.card_view_signin)
        mllcheckout_stepper = findViewById(R.id.llcheckout_stepper)
        mll_checkoutaddress_button = findViewById(R.id.ll_checkoutaddress_button)
        mcard_view_address_data = findViewById(R.id.card_view_address_data)
        productRealm=readProductRecords(realm)
        currentToken= CommonMethod.getCurrentToken(this).toString()
        prgDialog = ProgressDialog(this@ActivityTwo)
        prgDialog!!.setMessage("Please wait..")
        prgDialog!!.setCancelable(false)
      /*  val qty= CommonMethod.getQuantityValue(this)
        val startingIntent = intent
        val houseno: String? = startingIntent.getStringExtra("houseno")
        println("testhouseno" + houseno)
        println("testqty" + qty)

        address = Address()
        address.house_no_apart = qty
        addressList.add(address)
        //  i++
        //}.
        println("testqtysize" + addressList.size)
        //ADAPTER
        adapterAddress = AddressAdapter(baseContext, addressList)
        mcheckoutaddress.adapter = adapterAddress
        adapterAddress?.notifyDataSetChanged()
        //println("failtestproductout" + productRealm.size)
        //println("testproductqtys" + productqtys.size)*/

        mll_checkoutaddress_button.setOnClickListener(View.OnClickListener {
            if (checkValidation()) {
                saveAddressList()
            }
        })

        mll_login_button = findViewById(R.id.ll_login_button)

        mll_login_button.setOnClickListener(View.OnClickListener {
            println("failtestproduct" + productRealm.size)
            if (productRealm.size > 0) {
                var intent = Intent(this, ProducDetailActivity::class.java)
                startActivity(intent)
               /* mcard_view_signin.setVisibility(View.GONE);
                mllcheckout_stepper.setVisibility(View.VISIBLE);
                mcard_view_address_data.setVisibility(View.VISIBLE);*/

            }
        })


        if(currentToken.length>0){
            mcard_view_signin.setVisibility(View.GONE);
        }else{
            mcard_view_signin.setVisibility(View.VISIBLE);
        }

    mbt_signin.setOnClickListener(View.OnClickListener {
           // println("failtestproduct" + productRealm.size)
           // if (productRealm.size > 0) {
              var intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)

                //mcard_view_signin.setVisibility(View.GONE);
               // mllcheckout_stepper.setVisibility(View.GONE);
            //}
        })
        mbt_register.setOnClickListener(View.OnClickListener {
            println("failtestproduct" + productRealm.size)
           // if (productRealm.size > 0) {
               var intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
              //  mcard_view_signin.setVisibility(View.GONE);
                //mllcheckout_stepper.setVisibility(View.VISIBLE);
                //mcard_view_address_data.setVisibility(View.VISIBLE);
          //  }
        })



        miv_addbtn.setOnClickListener(View.OnClickListener {
            println("failtestproduct" + productRealm.size)

          //  var intent = Intent(this, AddAddressActivity::class.java)
          //  startActivity(intent)
            showCustomAlert()
           /* if (productRealm.size > 0) {
                //var intent = Intent(this, RegisterActivity::class.java)
                //startActivity(intent)
                mcard_view_signin.setVisibility(View.GONE);
                mllcheckout_stepper.setVisibility(View.VISIBLE);
                mcard_view_address_data.setVisibility(View.VISIBLE);
            }*/
        })


        if (productRealm.size.toString() == "0") {
            mtvlogin.setText(" ")
        } else {
            mtvlogin.setText(productRealm.size.toString())
        }


        //SET ITS PROPETRIES
        mcheckoutaddress.layoutManager = LinearLayoutManager(this)
        mcheckoutaddress.itemAnimator = DefaultItemAnimator()
        getAddressList()
    }
    //Method Define To Check Validation
    private fun checkValidation(): Boolean {

        if (selected.length<1) {
            CommonMethod.showAlert("Please select delivery address before proceeding", this)
            return false
        }


        return true
    }
    fun saveAddressList() {
        val dataJson = JSONObject()
        dataJson.put("addressLine1",addressLine1)
        dataJson.put("addressLine2",addressLine2)
        dataJson.put("addressLine3",addressLine3)
        dataJson.put("city",city)
        dataJson.put("country",null)
        dataJson.put("geolocation",null)
        dataJson.put("mobileNumber",mobileNumber)
        dataJson.put("name",name)
        dataJson.put("pin",null)
        dataJson.put("pinCode",pinCode)
        dataJson.put("selected",true)
        dataJson.put("state",state)
        System.out.println("successdataJson"+dataJson);
        val se = StringEntity(dataJson.toString(), "UTF-8")
        val client = AsyncHttpClient()
        client.addHeader("Referer", "www.bestonhealth.in")
        client.addHeader("Authorization", "Bearer"+" "+currentToken)
        client.post(
            this,
            "https://api.bestonhealth.in/IPMService/api/marketplace/cart/delivery-address",
            se,
            "application/json;charset=utf-8",
            object : AsyncHttpResponseHandler() {
                override fun onStart() {
                    super.onStart()
                    this@ActivityTwo.prgDialog?.show()
                }
                override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                    // Log.e("success_noti", String(responseBody) + "")
                    // super.onSuccess(statusCode, headers, responseBody)
                    this@ActivityTwo.prgDialog?.dismiss()

                    val response = String(responseBody);
                    //  val json = JSONObject(responseBody);
                    System.out.println("success_noti"+response);
                    val jsonResponse = JSONObject(response)
                    val jsonSuccess=jsonResponse.getBoolean("success")
                      if(jsonSuccess){
                          var intent = Intent(this@ActivityTwo, CheckoutPrescriptionActivity::class.java)
                          startActivity(intent)
                          this@ActivityTwo.overridePendingTransition(R.anim.enter, R.anim.exit)
                      }

                    }




                override fun onFailure(
                    statusCode: Int,
                    headers: Array<Header>,
                    responseBody: ByteArray,
                    error: Throwable
                ) {
                    // Log.e("fail_noti", String(responseBody) + "")
                    prgDialog?.dismiss()
                    System.out.println("fail_noti"+responseBody);
                }
            })


    }


    fun getAddressList() {

        val dataJson = JSONObject()
       // dataJson.put("password","india@5555" )
        System.out.println("successdataJson"+dataJson);
        val se = StringEntity(dataJson.toString(), "UTF-8")
        val client = AsyncHttpClient()
        client.addHeader("Referer", "www.bestonhealth.in")
        client.addHeader("Authorization", "Bearer"+" "+currentToken)
        client.get(
            this,
            "https://api.bestonhealth.in/IPMService/api/users/address-list",
            se,
            "application/json;charset=utf-8",
            object : AsyncHttpResponseHandler() {
                override fun onStart() {
                    super.onStart()
                    this@ActivityTwo.prgDialog?.show()
                }
                override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                    // Log.e("success_noti", String(responseBody) + "")
                    // super.onSuccess(statusCode, headers, responseBody)
                    this@ActivityTwo.prgDialog?.dismiss()

                    val response = String(responseBody);
                    //  val json = JSONObject(responseBody);
                   System.out.println("success_noti"+response);
                   val jsonResponse = JSONObject(response)
                    val jsonDataArrayRes=jsonResponse.getJSONArray("data")
                    System.out.println("success_dataJsonResponse"+jsonDataArrayRes);

                    var i=0
                    while(jsonDataArrayRes.length()>i) {
                        val addressList_data = jsonDataArrayRes.getJSONObject(i)
                        address = Address()
                        val addressLine1 = addressList_data.getString("addressLine1")
                        val addressLine2 = addressList_data.getString("addressLine2")
                        val addressLine3 = addressList_data.getString("addressLine3")
                        val city = addressList_data.getString("city")
                        val pin = addressList_data.getString("pin")
                        val pinCode = addressList_data.getString("pinCode")
                        val state = addressList_data.getString("state")
                        val country = addressList_data.getString("country")
                        val name = addressList_data.getString("name")
                        val mobileNumber = addressList_data.getString("mobileNumber")
                        val geoLocation = addressList_data.getString("geoLocation")
                        address.contact_person=name
                        address.mobileNumber=mobileNumber
                        address.house_no_apart=addressLine1
                        address.street=addressLine2
                        address.landmark=addressLine3
                        address.city=city+" - "+pinCode
                        address.cityNew=city
                        address.state=state
                        address.pincode=pinCode
                        addressList.add(address)
                        i++
                    }

                    //val id = jsonLoginDetails.getString("id")

                    /* address = Address()
               address.house_no_apart = met_houseno_floor.text.toString()
               addressList.add(address)*/
               //ADAPTER
               adapterAddress = AddressAdapter(baseContext, addressList)
               mcheckoutaddress.adapter = adapterAddress

                    adapterAddress?.itemAddListner=object :OnIAddressAddRemoveListener{
                        override fun onAddAddress(position: Int, item: Address) {
                             addressLine1= item.house_no_apart.toString()
                              addressLine2=item.street.toString()
                             addressLine3=item.landmark.toString()
                             pinCode=item.pincode.toString()
                             city=item.cityNew.toString()
                           state=item.state.toString()
                            mobileNumber=item.mobileNumber.toString()
                            name=item.contact_person.toString()
                            selected="true"

                        }

                    }

               adapterAddress?.notifyDataSetChanged()

                }

                override fun onFailure(
                    statusCode: Int,
                    headers: Array<Header>,
                    responseBody: ByteArray,
                    error: Throwable
                ) {
                    // Log.e("fail_noti", String(responseBody) + "")
                    prgDialog?.dismiss()
                    System.out.println("fail_noti"+responseBody);
                }
            })


    }



    private fun showCustomAlert() {
        val dialogView = layoutInflater.inflate(R.layout.activity_addaddress, null)
        val customDialog = AlertDialog.Builder(this)
                .setView(dialogView)
                .show()

        val met_houseno_floor = dialogView.findViewById<EditText>(R.id.et_houseno_floor)
        val met_street = dialogView.findViewById<EditText>(R.id.et_street)
        val met_landmark = dialogView.findViewById<EditText>(R.id.et_landmark)
        val met_pincode = dialogView.findViewById<EditText>(R.id.et_pincode)
        val met_city = dialogView.findViewById<EditText>(R.id.et_city)
        val met_state = dialogView.findViewById<EditText>(R.id.et_state)
        val met_contactperson = dialogView.findViewById<EditText>(R.id.et_contactperson)
        val met_mobile_number = dialogView.findViewById<EditText>(R.id.et_mobile_number)
        val ll_saveaddress_btn = dialogView.findViewById<LinearLayout>(R.id.ll_saveaddress_btn)
        ll_saveaddress_btn.setOnClickListener {



            println("testmethouseno" + met_houseno_floor.getText().toString())

            if (met_houseno_floor.getText().toString().trim({ it <= ' ' }).length == 0) {
                met_houseno_floor.setError("Please Enter House Number")
            }else if (met_street.getText().toString().trim({ it <= ' ' }).length == 0) {
                met_street.setError("Please Enter Street")
            }else if (met_landmark.getText().toString().trim({ it <= ' ' }).length == 0) {
                met_landmark.setError("Please Enter Landmark")
            }else if (met_pincode.getText().toString().trim({ it <= ' ' }).length == 0) {
                met_pincode.setError("Please Enter Pincode")
            }else if (met_city.getText().toString().trim({ it <= ' ' }).length == 0) {
                met_city.setError("Please Enter City")
            }else if (met_state.getText().toString().trim({ it <= ' ' }).length == 0) {
                met_city.setError("Please Enter State")
            }else if (met_contactperson.getText().toString().trim({ it <= ' ' }).length == 0) {
                met_contactperson.setError("Please Enter Contact Person Name")
            }else if (met_mobile_number.getText().toString().trim({ it <= ' ' }).length == 0) {
                met_mobile_number.setError("Please Enter Mobile Number")
            }else {
               /* address = Address()
                address.house_no_apart = met_houseno_floor.text.toString()
                addressList.add(address)
                //ADAPTER
                adapterAddress = AddressAdapter(baseContext, addressList)
                mcheckoutaddress.adapter = adapterAddress
                adapterAddress?.notifyDataSetChanged()*/

                val addressLine1 = met_houseno_floor.getText().toString()
                val addressLine2 = met_street.getText().toString()
                val addressLine3 = met_landmark.getText().toString()
                val pinCode = met_pincode.getText().toString()
                val city = met_city.getText().toString()
                val state = met_state.getText().toString()
                val mobileNumber = met_mobile_number.getText().toString()
                val name = met_contactperson.getText().toString()

                val dataJson = JSONObject()
                dataJson.put("addressLine1", addressLine1)
                dataJson.put("addressLine2",addressLine2 )
                dataJson.put("addressLine3", addressLine3)
                dataJson.put("pinCode",pinCode )
                dataJson.put("city", city)
                dataJson.put("state",state)
                dataJson.put("mobileNumber", mobileNumber)
                dataJson.put("name",name)
                System.out.println("successdataJson"+dataJson);

                val se = StringEntity(dataJson.toString(), "UTF-8")

                val client = AsyncHttpClient()
                client.addHeader("Referer", "www.bestonhealth.in")
                client.addHeader("Authorization", "Bearer"+" "+currentToken)
                client.post(
                    this,
                    "https://api.bestonhealth.in/IPMService/api/users/address",
                    se,
                    "application/json;charset=utf-8",
                    object : AsyncHttpResponseHandler() {
                        override fun onStart() {
                            super.onStart()
                            this@ActivityTwo.prgDialog?.show()
                        }
                        override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                            // Log.e("success_noti", String(responseBody) + "")
                            // super.onSuccess(statusCode, headers, responseBody)
                            this@ActivityTwo.prgDialog?.dismiss()

                            val response = String(responseBody);
                            //  val json = JSONObject(responseBody);
                            System.out.println("addresuccess_noti"+response);
                            customDialog.dismiss()
                           // getAddressList()
                            /*      val jsonResponse = JSONObject(response)
                                  val jsonDataResponse=jsonResponse.getString("data")
                                  System.out.println("success_dataJsonResponse"+jsonDataResponse);
                                  val jsonLoginDetails = JSONObject(jsonDataResponse)
                                 // val id = jsonLoginDetails.getString("id")
                                  //System.out.println("Dataddd"+""+id);

                                  if(jsonLoginDetails.has("id")) {
                                       id = jsonLoginDetails.getString("id")
                                  }
                                  if(jsonLoginDetails.has("name")) {
                                       name = jsonLoginDetails.getString("name")
                                  }
                                  if(jsonLoginDetails.has("emailAddress")) {
                                       emailAddress = jsonLoginDetails.getString("emailAddress")
                                  }
                                  if(jsonLoginDetails.has("mobileNumber")) {
                                       mobileNumber = jsonLoginDetails.getString("mobileNumber")
                                  }
                                  if(jsonLoginDetails.has("gender")) {
                                      val gender = jsonLoginDetails.getString("gender")
                                  }
                                  if(jsonLoginDetails.has("dateOfBirth")) {
                                      dateOfBirth = jsonLoginDetails.getString("dateOfBirth")
                                  }
                                  if(jsonLoginDetails.has("profilePicture")) {
                                       profilePicture = jsonLoginDetails.getString("profilePicture")
                                  }
          */
                            // val dataJsonResponse=JSONObject(jsonResponse)

                            //    System.out.println("Data"+""+id+""+name+""+emailAddress+""+mobileNumber+""+gender+""+dateOfBirth+""+profilePicture)




                            // All writes must be wrapped in a transaction to facilitate safe multi threading
                            /*  realm.executeTransaction { realm ->

                                  val maxId = realm.where(LoginDetail::class.java).max("id")
                                  // If there are no rows, currentId is null, so the next id must be 1
                                  // If currentId is not null, increment it by 1
                                  val nextId = if (maxId == null) 1 else maxId!!.toInt() + 1
                                 // val name=met_user_name.text
                                  val pass= met_password.text
                                 val userDetail = realm.createObject(LoginDetail::class.java, nextId)
                                  // Add a person
                                  // val person = realm.createObject<Person>(nextId)
                                 // userDetail.id = id
                                  userDetail.name = name.toString()
                                  userDetail.emailAddress = emailAddress.toString()
                                  userDetail.mobileNumber = mobileNumber
                                  userDetail.gender = gender
                                  userDetail.dateOfBirth = dateOfBirth
                                  userDetail.profilePicture = profilePicture

                              }*/


                        }

                        override fun onFailure(
                            statusCode: Int,
                            headers: Array<Header>,
                            responseBody: ByteArray,
                            error: Throwable
                        ) {
                            // Log.e("fail_noti", String(responseBody) + "")
                            prgDialog?.dismiss()
                            System.out.println("fail_noti"+responseBody);
                        }
                    })





               // saveAddress()
                //customDialog.dismiss()
            }


        }
    }




    private fun readProductRecords(realm: Realm):  ArrayList<ProductRealm>  {

        var productRealmList = ArrayList<ProductRealm>()
        realm.executeTransaction( { realm ->

            val results = realm.where(ProductRealm::class.java).findAll()
            for (productQty in results) {
             //   println("failidtestnew"+productQty.id.toString()+"  name  "+productQty.name + " productskuCode: " + productQty.skuCode + " productCount: "+ productQty.count+ " productCountProduct: "+ productQty.countProduct)
                productRealmList.add(ProductRealm(productQty.id,productQty.productId,productQty.name,productQty.skuCode,productQty.skuUnit,productQty.skuForm,productQty.skuQuantity,productQty.skuType,
                        productQty.skuMRP,productQty.skuPTR,productQty.componentName,productQty.quantity,productQty.units,productQty.img,productQty.price,productQty.count,productQty.countProduct))

            }
        })
        return productRealmList
    }

    private fun setupStoreData() {
        /*productqtys = databaseQueryClass.allProductQuantityList
        for (saveItem in productqtys) {
            println("testQuantitylittle" + saveItem.id)

            println("testQuantitylittleskuCode" + saveItem.skuCode)
            println("testQuantitylittlecountProductQuantity" + saveItem.countProductQuantity)
        }*/
    }
    override fun onResume() {
        super.onResume()
      //  setupStoreData()

        mtvlogin = findViewById(R.id.tvlogin)
        if (productRealm.size.toString() == "0") {
            mtvlogin.setText(" ")
        } else {
            mtvlogin.setText(productRealm.size.toString())
        }

    }




    override fun onDestroy() {
        super.onDestroy()
        realm.close() // Remember to close Realm when done.
    }






override fun onBackPressed() {
super.onBackPressed()
val intent = Intent(this, MainActivity::class.java)
this.finish();
startActivity(intent)
this@ActivityTwo.overridePendingTransition(R.anim.enter, R.anim.exit)

}

private fun setToolBar() {
mtoolbar = findViewById(R.id.toolbar)
this.mtoolbar.setNavigationIcon(resources.getDrawable(R.drawable.ic_back))
mtoolbar.setNavigationOnClickListener(View.OnClickListener { onBackPressed() })
}


}
