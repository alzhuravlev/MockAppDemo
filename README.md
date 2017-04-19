# MockAppDemo

Why not give yourself the opportunity to change the user interface "on the fly" without updating the application in the market?
This library is intended just to solve this problem.

The sequence of actions is approximately the following:
- Create a UI layout in <a href="https://play.google.com/store/apps/details?id=com.crane.mockapp">MockApp</a>
- Unload the layout (s) as a ZIP file and put it on our web-server
- Add to the project android application dependencies <code>compile 'com.crane:mockappcore:0.1'</code>
- Download zip file
- Import layout <code>ProjectServiceFactory.getInstance(context).importZip(Path to ZIP file);</code>
- Inflate a view <code>LayoutInflater.inflate(context, "Cards", "LargeMedia_1_1", parent, true);</code>
- If necessary, set properties / event handlers (see MockAppDemo project for examples)

Of course, it makes sense to download the ZIP file for the first time, and then only if it was changed.

# Design tool & Samples

In the <a href="https://play.google.com/store/apps/details?id=com.crane.mockapp"> MockApp </a> app
you can find many examples of UI

# Developed by
    Alexey Zhuravlev (crane2002)