# MockAppDemo

Why not give yourself the opportunity to change the user interface "on the fly" without updating the application in the market?
This library is intended just to solve this problem.

The sequence of actions is approximately the following:
- Create a UI layout in <a href="https://play.google.com/store/apps/details?id=com.crane.mockapp">MockApp</a>
- Unload the layout (s) as a ZIP file and put it on your web-server
- Add to the project android application dependency <code>compile 'com.crane:mockappcore:0.2'</code>
- Download ZIP file with layouts from your web server
- Import layout <code>ProjectServiceFactory.getInstance(context).importZip(Path to ZIP file);</code>
- Inflate a view <code>LayoutInflater.inflate(context, "Cards", "LargeMedia_1_1", parent, true);</code>
- If necessary, set properties / event handlers (see MockAppDemo project for examples)

Of course, it makes sense to download and import the ZIP file only once (and then only if it was changed)

# Design tool & Samples

In the <a href="https://play.google.com/store/apps/details?id=com.crane.mockapp"> MockApp </a> app
you can find many examples of UI

# Developed by
    Alexey Zhuravlev (crane2002)