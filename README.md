# MockApp Demo

This is a demo of the MockApp library used to inflate layouts created with 
[MockApp application](https://play.google.com/store/apps/details?id=com.crane.mockapp)

# Motivation

Traditionaly UI prototypes are created using great tools like zeplin, figma etc. Developer takes that assets and trying
to recreate UI using Android layout XML. If something changed cycle has to be repeated. So we have two bunches of assets: produced by designing tool and Androdi XML that needs to be in keep sync. As we all know that the shorter the dev cycle the better.

So why we couldn't experimenting with UI right in Android UI terms (Views)? Wnen design assets become real app assets, not just a nice pictures.

The challenge of this tool is to have one bunch of assets for design and production code.

# Usage

General steps are:
1. Design initial UI, quick sending results to the stakeholders as APK generated right from the [MockApp application](https://play.google.com/store/apps/details?id=com.crane.mockapp)
2. Use excactly the same UI in real Android Studio project. Using [ MockApp Library ![MockApp Library](https://api.bintray.com/packages/crane2002/maven/mockapp-core/images/download.svg?_latestVersion) ](https://bintray.com/crane2002/maven/mockapp-core/_latestVersion/link) to **inflate layouts** and **bind views**
3. Make changes to the UI and see results immediately in your app

# Design time

1. Copy-paste. You can copy from any project/layout and paste it into any layout. Views will be independent from where tou take it
2. Reuse your layouts. Extract general parts of UI and reuse it. You have an ability to include layout into another layout. Any changes in the source layout will be propogated into any place you use it
3. Undo last operation
4. SVG support
5. Nine patch support
6. Gradient/Image/Tile backgtound
7. Material themes support
8. Elevation/Outline (round corners)
9. All major Android layouts: Frame, Linear, Toolbar, Scroll, Coordinator, AppBar, BottomSheet, NavigationDrawer and others
10. Full screen supported
11. Status bar coloring
12. And more...

Some design technics are highlited here [MockApp. Design Time](https://medium.com/mock-app/mockapp-design-time-1aeecbcaf858)

# Code time

## Dependencies
[ ![Download](https://api.bintray.com/packages/crane2002/maven/mockapp-core/images/download.svg?_latestVersion) ](https://bintray.com/crane2002/maven/mockapp-core/_latestVersion/link)

Add dependencies
```gradle
    // required
    implementation 'com.crane:mockappcore:1.40.5'

    // optional: for auto build *.tags.txt file from classes
    // annotated with @MockAppLayout and @MockAppView
    kapt 'com.crane:mockappprocessor:1.40.5'
```

## Initialization

This step is optional. By default projects are searched in following path:
* local projects in internal storage of the device: /sdcard/Documents/MockApp
* internal projects in **assets** folder of the app: /assets/MockApp

You can get very fast dev cycle if you inflate layouts from internal storage. But production app get layouts from assets folder.

You can change local ptoject path in this way:

```kotlin
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // Optionally you can override location of the local project's storage.
        // Default is /sdcard/Documents/MockApp
        val path =
            File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "MyMockAppFolder").path
        ProjectServiceFactory.init(this, path)
    }
}
```
## Inflate layout using MockAppActivity

`MockAppActivity` do a lot of useful things: coloring status and nav bars, controls full screen mode, inflate bottom sheet and nav drawer for you, apply themes. At this moment it is very recommend to inherit your activity from it. But you still able to inflate layout without it with several lines of code (see Inflate layout in general).

### [Activity1.kt](app/src/main/java/com/crane/mockappdemo/sample1/Activity1.kt)

If you know project/layout name in advance you can use annotation `@MockAppLayout`

```kotlin
@MockAppLayout(projectName = "icountries", layoutName = "view_country")
class Activity1 : MockAppActivity() {
}
```

Just start `Activity1` and you get ready to use UI loaded from [view_country.json](app/src/main/assets/MockApp/icountries/view_country.json)

OR 

if your app has READ_EXTERNAL_STORAGE permission and file `/sdcard/Documents/MockApp/icountries/view_country.json` exists then layout will be inflated from this location

### [Activity2.kt](app/src/main/java/com/crane/mockappdemo/sample1/Activity2.kt)

To let your code resolve what project/layout to infate in runtime just override (`getProjectId` OR `getProjectName`) AND (`getLayoutId` OR `getLayoutName`)

```kotlin
    /**
     * You can override getProjectId or getProjectName to pass a Project to find a layout in
     * @see com.crane.mockapp.core.MockApp.resolveLayoutDescriptor
     */
    override fun getProjectId(): String? {
        return null
    }

    override fun getProjectName(): String? {
        return "icountries"
    }

    /**
     * You can override getLayoutId or getLayoutName to find a layout to inflate
     * @see com.crane.mockapp.core.MockApp.resolveLayoutDescriptor
     */
    override fun getLayoutId(): String? {
        return null
    }

    override fun getLayoutName(): String? {
        return "page_home"
    }
```

If layout could not be inflated for any reason you have a chance to take care of it overriding `inflateDefaultLayout`

```kotlin
    override fun inflateDefaultLayout() {
        // if for any reason layout count not be inflated this method will be invoked
    }
```

## Inflate layout using MockAppFragment

### [Fragment3.kt](app/src/main/java/com/crane/mockappdemo/sample1/Fragment3.kt)

Just like extending MockAppActivity you have options to use
@MockAppLayout or override getProjectId/getProjectName and getLayoutId/getLayoutName

## Inflate layout in general
## Inflate RecyclerView's items
## Binding views

# Developed by
Alexey Zhuravlev ([crane2002@gmail.com](mailto:crane2002@gmail.com))

# License

    Copyright 2019 Alexey Zhuravlev
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
       http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

    
    
