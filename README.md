# gstest
**Project details:** 
NASa Image Of day

**Technology Used:**
Kotlin
Jetpack 
 - Room db
 - Compose
 
 **Architecture**
 MVVM + Clean
 - data
   - api - Api related classes
   - database - Database related classes
   - model - Model classes
   - repository - Network and Database handler classes
 - di - Dagger 2 used for dependency injection
 - ui - Activity and UI components
 - viewmodel - View models
 
 **Project Set up steps- **
 - git clone https://github.com/nimbalkarsujata/gstest.git
 - Open project in Android Studio - Trust Project
 - Install the application
 
 **Test cases covered:**
 - Ui 
 - Unit
 
** Application Walkthrough - **
 Home Screen - Here Provison to add in favourites on click of Favourite  icon in Item details: 
 <img width="279" alt="Screenshot 2022-09-11 at 1 19 23 AM" src="https://user-images.githubusercontent.com/88527189/189499649-933e47c8-a6a1-4cdd-8536-4de0a5cd5c57.png">

 On Top Bar Favourite  Icon lick it will open List of all favourite  items: on deselection it will remove item from list
 <img width="283" alt="Screenshot 2022-09-11 at 1 19 35 AM" src="https://user-images.githubusercontent.com/88527189/189499648-5eda090c-df07-484b-81e5-47d50a74d532.png">

 On Top bar Calender icon click it will open calender form date selection :
 
 <img width="284" alt="Screenshot 2022-09-11 at 1 20 02 AM" src="https://user-images.githubusercontent.com/88527189/189499645-853a4168-b5fa-4e82-b5d0-0eb541985e23.png">

 On selction of date it will render the selected date item details: 
 
 <img width="275" alt="Screenshot 2022-09-11 at 1 20 18 AM" src="https://user-images.githubusercontent.com/88527189/189499639-338049a6-98b3-461c-a65b-c7cf646a11ae.png">

  
  
