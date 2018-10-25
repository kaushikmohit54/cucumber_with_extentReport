Feature: Login Action
 
Scenario: Successful Login with Valid Credentials
	Given User is on Home Page
	When User Navigate to LogIn Page
	And User enters UserName and Password
    When User clicks on Sign In button
	Then Verify Successfully logged in
	When User LogOut from the Application
	Then Message displayed LogOut Successfully
 

	