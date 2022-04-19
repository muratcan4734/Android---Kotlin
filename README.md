# Android-Kotlin
Building an Expenses Tracker
This project deals with adding income and expenses. The user has both options available for adding income and expense. But there is a condition if the user hasn’t entered the income of the month yet then the user can’t enter expenses for this month. When the user enters any transaction then that transaction will be added in both DATE and EXPENSE table in the database. If the user wants to delete or update that month then the user has to click the "Delete", “Update” Button available in the activity_monthly_expenses.xml layout then that transaction will be deleted and updated the Income of the month from “DATE” AND “EXPENSE” both tables in the database. If the user does not select the month and year in the spinner of activity_main.xml layout, the current month and year is automatically added to a create new sheet after entering an income.
Users can create multiple expenses monthly. User has the option available for creating a new expense.  Users will click the “+” sign button in the activity_monthly_expenses.xml layout then a dialog will appear on the screen and the user can enter the name, amount and regular status of the expense then these datas will be saved in the EXPENSE DATABASE. If a user wants to delete or update data of the expense then the user has to click the expense card user want to go activity_expense_detail.xml layout. Then that user can the expense will be change info. 
The user can filter the transactions for Expense amount. If the user wants to filter the transactions only on the basis of month, for example, user-clicked "Regular Expense Only" Button  in the activity_monthly_expenses.xml layout then all transactions will calculate Total Money Regular Expense Monthly.
CODE METHOD

1.	Design Layout Pages
First of all, I started my project by creating the design layout pages. 

 	activity_main.xml

I needed 2 pcs Spinner for User can select Month and Year.
If User does not choose these spinner automatically added current name of month and year to a create new sheet
I created an Edit Text and input type is number decimal 
I used a clickable Recycler View to show for each data of month, this shows the surplus/deficit and so User can follow status of each month.
I've explained how these method below.


 	activity_monthly_expenses.xml

This page becomes active when the user clicks on each month row of the recycler view in the activity_main.xml
I created an intent when user clicked each month information. I added this incoming data to the appropriate Text View on this page.
The user can change the income amount of the month or delete this month, I created the necessary palettes for this.
I used a clickable Recycler View to show for each expense inside this month
I created  "+" a button for create a new Expense "Regular Expense Only" this button calculates and shows Total Regular Expense

 	activity_create_expense.xml

If the User wants to create a new expense in that month, Can click the (+) button in activity_monthly_expense.xml
When create a new expense I added Data Base Information received from the activity_month_expense.xml on intent so I will know what date the new expense belongs to

 	activity_expense_detail.xml

This page becomes active when the user clicks on each expense row of the recycler view in the activity_monthly_expenses.xml
I created an intent when user clicked each expense information. I added this incoming data to the appropriate Edit Text and Text View on this page. I created the necessary palettes for this.
The user can change the expense or delete this expense.



 	 	layout_list_month.xml
layout_list_expense.xml

I used the Card View. With it, I created a Expense List And Month List with Recycler View using Card View. It can show a list of items in Horizontal.
