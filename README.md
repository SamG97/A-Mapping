#Langton Map App
##Introduction

This application has been built to aid in navigation around the school. You can enter two locations into the app using a button based menu and the app will find the shortest way to get from one to the other.

The app is aimed at new students and staff and visitors that are unfamiliar with the site. The app contains most rooms and locations in the school that these users are likely to want to navigate between but there are some locations that the app does not cover such as various offices and storage rooms. The app will provide a list of text based instructions which should adequately describe the route but there is no image output.

##Installation

![ss](https://i.imgur.com/QDk0DTL.png)

This app has been built to work best on the Android API level 23 (version 6.0 - Marshmallow) but will work on API level 19 (version 4.4 - KitKat) or later. The app will not work on iOS (Apple), Windows or other operating systems.

In order to use the app, you will first need to enable apps to be installed from unknown sources, if this has not been already done on the device that you wish to use it on. To do this, navigate to the settings menu of your Android phone or tablet and go to the security tab. Tick the box under device administration labelled ‘unknown’ sources. This will allow the app to be installed without using the Play store.

You will then to download the app from the releases tab. Navigate to this page using a web browser such as Chrome on your phone or tablet and then click on the download button. Once downloaded, click the file in your Downloads folder to install the app.

If you have problems in downloading the app or cannot connect to your internet with your phone or tablet, the app can be downloaded on a computer and then transferred to your device via a USB cable. The app can then be installed as before.

##Usage

If using a version before the latest version of Android (6.0), some features may be displayed differently. Their functionality will be the same as described here but will look different.

![ss](https://i.imgur.com/fixi5N4.png)

Once the app has been installed using the provided .apk file, open the app to the main menu. By clicking on the options menu, the choice of whether to allow stairs to be used will be shown. By putting the slider in the on position, only wheelchair accessible routes will be displayed. By default, this mode is off and all routes will be displayed. The back button can be pressed to return to the main menu.

![ss](https://i.imgur.com/WZKR3KC.png)

To find a route, press the start button from the main menu. You will then need to enter the location you would begin the route from. This menu is shown above. You may click on either the common locations button to see a list of places you may want to visit in the school other than classrooms (such as reception, various sporting facilities and the hall). After clicking this button, simply scroll through the options and press the desired choice. There is a back button at the bottom of the list if you change your mind. Alternatively, the classrooms button can be pressed to bring up a list of the various blocks around the school. Click the block that the desired classroom is in, for example if staring from B3, click the B button. Again, a back button is at the bottom of the menu. A list of all classroom numbers in the block will be displayed once a block has been chosen; the desired classroom can then be selected. In the example earlier, 3 should be pressed. An example of these menus is shown below.

![ss](https://i.imgur.com/tdoP32Z.png)
![ss](https://i.imgur.com/NLBwJjP.png)
![ss](https://i.imgur.com/6zggjfd.png)

Once a start location has been selected, a destination must be chosen. To do this, follow the same procedure as when entering the start location. The option to navigate to the nearest toilet is also displayed at this point with the option of choosing men’s, women’s and disabled toilets if this option is chosen (shown below). If the back button is chosen to return to the main screen, your start location will be disregarded and will need to be entered again.

![ss](https://i.imgur.com/kY2CSd3.png)
![ss](https://i.imgur.com/ej9cnHF.png)

After both locations have been entered, the app will calculate the shortest possible route between the two points. This will be displayed as text based instructions guiding you to your destination. If the route is too long to fit onto one screen, you can scroll to read later instructions. At the end of the route, the total length of the route is displayed. Once you are done with the route, you can click the back button to return to the main menu and enter another route. This is shown above.

If no route can be found, this will also be shown on this display.

![ss](https://i.imgur.com/0U1W6Rv.png)
![ss](https://i.imgur.com/XeHB8Va.png)
![ss](https://i.imgur.com/i8cpWYD.png)

##Maintenance and Troubleshooting

Under normal usage, the app should not need any client-side maintenance, but should an error occur, please contact the developer with the conditions that lead to the error so that it can be corrected. The issues tracker is the preferred form of contact. Should the app state that no route could be found but you think this is in error or you think a longer or incorrect route is being displayed, first check that the no stairs option is turned off as this may be preventing it from finding a route that involves stairs. If this does not fix the issue, as before, please contact the developer. Should the app be updated (such as if a new building is built or a room’s purpose changes), the app will need to be manually reinstalled using the method outlined in the installation section of this guide.

##Limitations

As mentioned in the introduction, this app does not include every possible location in the school to make it easier to find the more useful and common locations. This app is therefore less useful for users more familiar with the site looking to find more obscure rooms such as a lab prep room or the PE storage shed.

There are several rooms which have the same name and purpose around the school, most notably toilets. Based on the logic behind the app, it is not possible to select which one in specifically to be used as a start location or destination. This is why toilets can only be selected as a destination as this would, most times, select the wrong starting location.

The app uses a series of points that are locked to two perpendicular axes in order to represent the school digitally. For the inside of the school buildings, this works very well, however, for the outside parts of the school, especially the area around J block and the Pavilion which is at an angle to the rest of the school, this is less accurate so directions involving these areas should be treated with some degree of approximation. Additionally, all measurements in the school were made to the nearest metre so the route lengths may be slightly inaccurate and seen as an approximate length rather than a very accurate way of determining lengths around the school. 
