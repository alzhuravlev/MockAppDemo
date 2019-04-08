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

![sss](app/src/Capture.PNG)

# Code time

[ ![Download](https://api.bintray.com/packages/crane2002/maven/mockapp-core/images/download.svg?_latestVersion) ](https://bintray.com/crane2002/maven/mockapp-core/_latestVersion/link)

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

    
    
