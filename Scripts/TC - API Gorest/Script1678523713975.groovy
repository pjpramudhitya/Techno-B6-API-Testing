import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import groovy.json.JsonSlurper as JsonSlurper
import static org.assertj.core.api.Assertions.*

def user_id

// Create User
res_create_user = WS.sendRequest(findTestObject('Postman/Create User', [('name') : Name, ('email') : Email, ('gender') : Gender
            , ('status') : Status]))

if (WS.verifyResponseStatusCode(res_create_user, 201, FailureHandling.OPTIONAL)) {
    res_get_user = WS.sendRequest(findTestObject('Postman/Get User', [('email') : Email]))

    def jsonSlurper = new JsonSlurper()

    def jsonResponse = jsonSlurper.parseText(res_get_user.getResponseText())

    assertThat(jsonResponse.name.toString()).contains(Name)

    assertThat(jsonResponse.email.toString()).contains(Email)

    assertThat(jsonResponse.gender.toString()).contains(Gender)

    assertThat(jsonResponse.status.toString()).contains(Status)
	
	// Parsing user_id
	user_id = jsonResponse.id.toString()
	user_id = user_id.replace("[","")
	user_id = user_id.replace("]","")
	System.out.println("\n========Create User========\n"+
		"User ID\t: "+user_id+
		"\nName \t: "+ jsonResponse.name.toString()+
		"\nEmail \t: "+ jsonResponse.email.toString()+
		"\nGender \t: "+ jsonResponse.gender.toString()+
		"\nStatus \t: "+ jsonResponse.status.toString())
}

if (WS.verifyResponseStatusCode(res_create_user, 422, FailureHandling.OPTIONAL)) {
    print('Title/Body Cannot Be Empty !')
} else if (WS.verifyResponseStatusCode(res_create_user, 401, FailureHandling.OPTIONAL)) {
    print('Authorization Failed')
} else if (WS.verifyResponseStatusCode(res_create_user, 404, FailureHandling.OPTIONAL)) {
    print('Check Your Endpoint')
}

// Update User
res_update_user = WS.sendRequest(findTestObject('Postman/Update User', 
	[('id') : user_id,('new_name') : New_name, ('new_email') : New_email, ('new_gender') : New_gender
            , ('new_status') : New_status]))

if (WS.verifyResponseStatusCode(res_update_user, 200, FailureHandling.OPTIONAL)) {
	res_get_user = WS.sendRequest(findTestObject('Postman/Get User', [('email') : New_email]))
	
	def jsonSlurper = new JsonSlurper()
	
	def jsonResponse = jsonSlurper.parseText(res_get_user.getResponseText())
	
	assertThat(jsonResponse.name.toString()).contains(New_name)
	
	assertThat(jsonResponse.email.toString()).contains(New_email)
	
	assertThat(jsonResponse.gender.toString()).contains(New_gender)
	
	assertThat(jsonResponse.status.toString()).contains(New_status)
	
	System.out.println("\n========Update User========\n"+
		"User ID\t: "+user_id+
		"\nName \t: "+ jsonResponse.name.toString()+
		"\nEmail \t: "+ jsonResponse.email.toString()+
		"\nGender \t: "+ jsonResponse.gender.toString()+
		"\nStatus \t: "+ jsonResponse.status.toString())
		
}

if (WS.verifyResponseStatusCode(res_update_user, 422, FailureHandling.OPTIONAL)) {
	print('Title/Body Cannot Be Empty !')
} else if (WS.verifyResponseStatusCode(res_update_user, 401, FailureHandling.OPTIONAL)) {
	print('Authorization Failed')
} else if (WS.verifyResponseStatusCode(res_update_user, 404, FailureHandling.OPTIONAL)) {
	print('Check Your Endpoint')
}

// Delete User
res_del_user = WS.sendRequest(findTestObject('Postman/Delete User', [('id') : user_id]))
WS.verifyResponseStatusCode(res_del_user, 204, FailureHandling.OPTIONAL)