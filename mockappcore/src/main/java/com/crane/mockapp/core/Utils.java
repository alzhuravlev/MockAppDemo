package com.crane.mockapp.core;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ViewAnimator;

import com.crane.mockapp.core.R;
import com.crane.mockapp.core.model.layouts.LayoutDescriptor;
import com.crane.mockapp.core.model.layouts.ProjectServiceFactory;
import com.crane.mockapp.core.model.layouts.PropEventChangePropValue;
import com.crane.mockapp.core.model.layouts.PropEventChangePropValues;
import com.crane.mockapp.core.model.layouts.PropEventValue;
import com.crane.mockapp.core.model.layouts.PropLayoutValue;
import com.crane.mockapp.core.model.props.PropModel;
import com.crane.mockapp.core.model.theme.ThemeModel;
import com.crane.mockapp.core.model.theme.ThemeModelColor;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

/**
 * Created by crane2002 on 1/14/2017.
 */

public class Utils {

    public static int dpToPxLayoutSize(Context context, float sizeDp) {
        if (sizeDp < 0f)
            return (int) sizeDp;
        return Math.round(sizeDp * context.getResources().getDisplayMetrics().scaledDensity);
    }

    public static int dpToPx(Context context, float sizeDp) {
        return Math.round(sizeDp * context.getResources().getDisplayMetrics().scaledDensity);
    }

    public static int pxToDpLayoutSize(Context context, float sizePx) {
        if (sizePx < 0f)
            return (int) sizePx;
        return Math.round(sizePx / context.getResources().getDisplayMetrics().scaledDensity);
    }

    public static int pxToDp(Context context, float sizePx) {
        return Math.round(sizePx / context.getResources().getDisplayMetrics().scaledDensity);
    }

    public static Object findTopMostCustomLayout(Object view) {
        if (view == null)
            return null;

        List<Object> views = new ArrayList<>();

        Object v = view;
        while (!isRootView(v)) {
            PropLayoutValue layoutValue = PropModel.CUSTOM_LAYOUT.getPropAs(v, PropLayoutValue.class);
            if (layoutValue != null)
                views.add(v);
            v = Utils.getParent(v);
        }

        if (views.size() > 0)
            return views.get(views.size() - 1);

        return null;
    }

    public static void clearPropLayoutValue(Object view) {
        if (view == null)
            return;

        PropModel.CUSTOM_LAYOUT.setProp(view, null);
        Utils.iterateChildren(view, new Utils.ViewChildrenIterator() {
            @Override
            public Object processChild(Object parent, Object child, int position) {
                clearPropLayoutValue(child);
                return null;
            }
        });
    }

    public static boolean hasCustomLayout(Object view) {
        return PropModel.CUSTOM_LAYOUT.getPropAs(view, PropLayoutValue.class) != null;
    }

    public static boolean hasCustomLayoutInParents(Object view) {
        if (view == null)
            return false;

        Object v = view;
        while (!isRootView(v)) {
            PropLayoutValue layoutValue = PropModel.CUSTOM_LAYOUT.getPropAs(v, PropLayoutValue.class);
            if (layoutValue != null)
                return true;
            v = Utils.getParent(v);
        }
        return false;
    }

    public static ScrollView findScrollViewInParents(Object view) {
        if (view == null)
            return null;

        Object v = view;
        while (!isRootView(v)) {
            if (v instanceof ScrollView)
                return (ScrollView) v;
            v = Utils.getParent(v);
        }
        return null;
    }

    public static ViewAnimator findViewAnimatorInParents(Object view) {
        if (view == null)
            return null;
        Object parent = getParent(view);
        if (parent != null && parent instanceof ViewAnimator)
            return (ViewAnimator) parent;
        return null;
    }

    public static ViewPager findViewPagerInParents(Object view) {
        if (view == null)
            return null;
        Object parent = getParent(view);
        if (parent != null && parent instanceof ViewPager)
            return (ViewPager) parent;
        return null;
    }

    public interface ViewChildrenIterator {
        Object processChild(Object parent, Object child, int position);
    }

    public static class Tab {
        public String text;
        public Drawable drawable;
        Object tag;
    }

    private static int menuItemSequence;

    private final static Comparator<MenuItem> MENU_ITEM_COMPARATOR = new Comparator<MenuItem>() {
        @Override
        public int compare(MenuItem o1, MenuItem o2) {
            return Integer.compare(o2.showAsActionFlag, o1.showAsActionFlag);
        }
    };

    public static class MenuItem {
        int id;
        public int showAsActionFlag = android.view.MenuItem.SHOW_AS_ACTION_ALWAYS;
        public String text;
        public Drawable drawable;
        Object tag;
    }

    public static Object iterateChildren(Object parent, ViewChildrenIterator childrenIterator) {
        return iterateChildren(parent, childrenIterator, false);
    }

    public static Object iterateChildren(Object parent, ViewChildrenIterator childrenIterator, boolean reverse) {
        if (parent == null || childrenIterator == null)
            return null;

        if (parent instanceof Toolbar) {
            Toolbar toolbar = (Toolbar) parent;

            List<MenuItem> items = (List<MenuItem>) getTag(toolbar, R.id.VIEW_MENU_ITEM_LIST_TAG);

            if (items != null)
                Collections.sort(items, MENU_ITEM_COMPARATOR);

            final int cnt = items == null ? 0 : items.size();
            final int startIdx = reverse ? cnt - 1 : 0;
            final int endIdx = reverse ? -1 : cnt;
            final int step = reverse ? -1 : 1;

            for (int i = startIdx; i != endIdx; i += step) {
                MenuItem menuItem = items.get(i);
                Object result = childrenIterator.processChild(parent, menuItem, i);
                if (result != null)
                    return result;
            }

            View customToolbarView = getCustomViewForToolbar(toolbar);
            if (customToolbarView != null) {
                Object result = childrenIterator.processChild(parent, customToolbarView, cnt);
                if (result != null)
                    return result;
            }

        } else if (parent instanceof TabLayout) {

            TabLayout tabLayout = (TabLayout) parent;

            List<Tab> tabList = (List<Tab>) getTag(tabLayout, R.id.VIEW_TAB_LIST_TAG);

            final int cnt = tabList == null ? 0 : tabList.size();
            final int startIdx = reverse ? cnt - 1 : 0;
            final int endIdx = reverse ? -1 : cnt;
            final int step = reverse ? -1 : 1;

            for (int i = startIdx; i != endIdx; i += step) {
                Tab tab = tabList.get(i);
                Object result = childrenIterator.processChild(parent, tab, i);
                if (result != null)
                    return result;
            }

        } else if (parent instanceof ViewPager) {

            ViewPager pager = (ViewPager) parent;
            ViewPagerAdapter adapter = (ViewPagerAdapter) pager.getAdapter();
            if (adapter == null)
                return null;

            final int cnt = adapter.getCount();
            final int startIdx = reverse ? cnt - 1 : 0;
            final int endIdx = reverse ? -1 : cnt;
            final int step = reverse ? -1 : 1;

            for (int i = startIdx; i != endIdx; i += step) {
                View page = adapter.getViewAt(i);
                Object result = childrenIterator.processChild(parent, page, i);
                if (result != null)
                    return result;
            }
        } else if (isViewGroup(parent)) {

            ViewGroup group = (ViewGroup) parent;
            int cnt = group.getChildCount();
            final int startIdx = reverse ? cnt - 1 : 0;
            final int endIdx = reverse ? -1 : cnt;
            final int step = reverse ? -1 : 1;

            for (int i = startIdx; i != endIdx; i += step) {
                Object result = childrenIterator.processChild(parent, group.getChildAt(i), i);
                if (result != null)
                    return result;
            }
        }
        return null;
    }

    public static boolean canAddViewToViewPager(Object view) {
        if (view == null || !(view instanceof View))
            return false;
        Class<?> clazz = view.getClass();
        return clazz.getAnnotation(ViewPager.DecorView.class) == null;
    }

    public static Object addViewToParent(Object parent, Object childToAdd, int index) {
        if (childToAdd != null)
            if (parent instanceof Toolbar) {
                Toolbar toolbar = (Toolbar) parent;
                if (childToAdd instanceof MenuItem) {
                    MenuItem menuItemToAdd = (MenuItem) childToAdd;

                    menuItemToAdd.id = ++menuItemSequence;
                    setTag(menuItemToAdd, R.id.VIEW_PARENT_TAG, toolbar);

                    List<MenuItem> items = (List<MenuItem>) getTag(toolbar, R.id.VIEW_MENU_ITEM_LIST_TAG);
                    if (items == null) {
                        items = new ArrayList<>();
                        setTag(toolbar, R.id.VIEW_MENU_ITEM_LIST_TAG, items);
                    }

                    if (index < 0)
                        index = items.size();

                    items.add(index, menuItemToAdd);
                    Collections.sort(items, MENU_ITEM_COMPARATOR);

                    toolbar.getMenu().clear();
                    for (MenuItem menuItemToAdd2 : items)
                        toolbar.getMenu()
                                .add(Menu.NONE, menuItemToAdd2.id, Menu.NONE, menuItemToAdd2.text)
                                .setIcon(menuItemToAdd2.drawable)
                                .setShowAsAction(menuItemToAdd2.showAsActionFlag);

                    return childToAdd;
                } else if (childToAdd.getClass().equals(LinearLayout.class)) {
                    if (getCustomViewForToolbar(toolbar) != null)
                        return null;
                    toolbar.addView((View) childToAdd);
                    return childToAdd;
                }
            } else if (parent instanceof TabLayout) {
                if (childToAdd instanceof Tab) {
                    TabLayout tabLayout = (TabLayout) parent;
                    Tab tabToAdd = (Tab) childToAdd;

                    List<Tab> tabList = (List<Tab>) getTag(tabLayout, R.id.VIEW_TAB_LIST_TAG);
                    if (tabList == null) {
                        tabList = new ArrayList<>();
                        setTag(tabLayout, R.id.VIEW_TAB_LIST_TAG, tabList);
                    }

                    if (index < 0)
                        index = tabList.size();

                    TabLayout.Tab tabNew = tabLayout.newTab();
                    tabNew.setText(tabToAdd.text);
                    tabNew.setIcon(tabToAdd.drawable);
                    tabLayout.addTab(tabNew, index);

                    tabList.add(index, tabToAdd);

                    setTag(childToAdd, R.id.VIEW_PARENT_TAG, tabLayout);

                    return childToAdd;
                }
            } else if (parent instanceof ViewPager) {
                if (canAddViewToViewPager(childToAdd)) {
                    ViewPager pager = (ViewPager) parent;
                    ViewPagerAdapter adapter = (ViewPagerAdapter) pager.getAdapter();
                    if (adapter == null) {
                        adapter = new ViewPagerAdapter();
                        pager.setAdapter(adapter);
                    }
                    setTag(childToAdd, R.id.VIEW_PARENT_TAG, pager);
                    adapter.addView((View) childToAdd, index);
                    return childToAdd;
                }
            } else if (isViewGroup(parent)) {
                if (childToAdd instanceof View) {
                    ViewGroup group = (ViewGroup) parent;
                    if (index < 0 || index > group.getChildCount())
                        index = group.getChildCount();
                    group.addView((View) childToAdd, index);
                    return childToAdd;
                }
            }
        return null;
    }

    public static Object addViewToParent(Object parent, Object childToAdd) {
        return addViewToParent(parent, childToAdd, -1);
    }

    public static Object removeViewFromParent(Object childToRemove) {
        if (childToRemove == null)
            return null;

        Object parent = Utils.getParent(childToRemove);

        if (parent != null)
            if (parent instanceof Toolbar) {
                Toolbar toolbar = (Toolbar) parent;
                if (childToRemove instanceof MenuItem) {
                    List<MenuItem> items = (List<MenuItem>) getTag(toolbar, R.id.VIEW_MENU_ITEM_LIST_TAG);
                    setTag(childToRemove, R.id.VIEW_PARENT_TAG, null);
                    if (items != null) {
                        items.remove(childToRemove);
                        Collections.sort(items, MENU_ITEM_COMPARATOR);
                        toolbar.getMenu().clear();
                        for (MenuItem menuItemToAdd2 : items)
                            toolbar.getMenu()
                                    .add(Menu.NONE, menuItemToAdd2.id, Menu.NONE, menuItemToAdd2.text)
                                    .setIcon(menuItemToAdd2.drawable)
                                    .setShowAsAction(menuItemToAdd2.showAsActionFlag);

                        return toolbar;
                    }
                } else if (childToRemove.getClass().equals(LinearLayout.class)) {
                    toolbar.removeView((View) childToRemove);
                    return toolbar;
                }
            } else if (parent instanceof TabLayout) {
                if (childToRemove instanceof Tab) {
                    TabLayout tabLayout = (TabLayout) parent;
                    List<Tab> tabList = (List<Tab>) getTag(tabLayout, R.id.VIEW_TAB_LIST_TAG);
                    if (tabList != null) {
                        int idx = tabList.indexOf(childToRemove);
                        if (idx != -1) {
                            tabList.remove(idx);
                            tabLayout.removeTabAt(idx);
                            setTag(childToRemove, R.id.VIEW_PARENT_TAG, null);
                            return tabLayout;
                        }
                    }
                }
            } else if (parent instanceof ViewPager) {
                if (childToRemove instanceof View) {
                    ViewPager pager = (ViewPager) parent;
                    ViewPagerAdapter adapter = (ViewPagerAdapter) pager.getAdapter();
                    setTag(childToRemove, R.id.VIEW_PARENT_TAG, null);
                    adapter.removeView((View) childToRemove);
                    return pager;
                }
            } else if (isViewGroup(parent)) {
                if (childToRemove instanceof View) {
                    ViewGroup group = (ViewGroup) parent;
                    group.removeView((View) childToRemove);
                    return group;
                }
            }

        return null;
    }

    public static Object getParent(Object child) {
        if (child == null)
            return null;

        Object parent = getTag(child, R.id.VIEW_PARENT_TAG);
        if (parent != null)
            return parent;

        if (child instanceof View) {
            parent = ((View) child).getParent();
            if (isViewGroup(parent))
                return parent;
        }

        return null;
    }


    public static int indexOfChild(Object child) {
        Object parent = getParent(child);

        if (parent == null)
            return -1;

        if (parent instanceof Toolbar) {
            if (child instanceof MenuItem) {
                List<MenuItem> items = (List<MenuItem>) getTag(parent, R.id.VIEW_MENU_ITEM_LIST_TAG);
                if (items != null) {
                    Collections.sort(items, MENU_ITEM_COMPARATOR);
                    return items.indexOf(child);
                }
            }
        } else if (parent instanceof TabLayout) {
            if (child instanceof Tab) {
                List<Tab> tabList = (List<Tab>) getTag(parent, R.id.VIEW_TAB_LIST_TAG);
                if (tabList != null)
                    return tabList.indexOf(child);
            }
        } else if (parent instanceof ViewPager) {
            if (child instanceof View) {
                ViewPager pager = (ViewPager) parent;
                ViewPagerAdapter adapter = (ViewPagerAdapter) pager.getAdapter();
                if (adapter != null)
                    return adapter.indexOfView((View) child);
            }
        } else if (isViewGroup(parent)) {
            if (child instanceof View) {
                ViewGroup group = (ViewGroup) parent;
                return group.indexOfChild((View) child);
            }
        }
        return -1;
    }

    public static int getChildCount(Object parent) {
        if (parent == null)
            return 0;

        if (parent instanceof Toolbar) {
            List<MenuItem> items = (List<MenuItem>) getTag(parent, R.id.VIEW_MENU_ITEM_LIST_TAG);
            return items == null ? 0 : items.size();
        } else if (parent instanceof TabLayout) {
            List<Tab> tabList = (List<Tab>) getTag(parent, R.id.VIEW_TAB_LIST_TAG);
            return tabList == null ? 0 : tabList.size();
        } else if (parent instanceof ViewPager) {
            ViewPager pager = (ViewPager) parent;
            ViewPagerAdapter adapter = (ViewPagerAdapter) pager.getAdapter();
            if (adapter != null)
                return adapter.getCount();
        } else if (isViewGroup(parent)) {
            ViewGroup group = (ViewGroup) parent;
            return group.getChildCount();
        }
        return 0;
    }

    public static Object getChildAt(Object parent, int index) {
        if (parent instanceof Toolbar) {
            List<MenuItem> items = (List<MenuItem>) getTag(parent, R.id.VIEW_MENU_ITEM_LIST_TAG);
            if (items != null) {
                Collections.sort(items, MENU_ITEM_COMPARATOR);
                return items.get(index);
            }
        } else if (parent instanceof TabLayout) {
            List<Tab> tabList = (List<Tab>) getTag(parent, R.id.VIEW_TAB_LIST_TAG);
            return tabList == null ? null : tabList.get(index);
        } else if (parent instanceof ViewPager) {
            ViewPager pager = (ViewPager) parent;
            ViewPagerAdapter adapter = (ViewPagerAdapter) pager.getAdapter();
            if (adapter != null)
                return adapter.getViewAt(index);
        } else if (isViewGroup(parent)) {
            ViewGroup group = (ViewGroup) parent;
            return group.getChildAt(index);
        }
        return null;
    }

    public static boolean isViewGroup(Object view) {
        if (view == null)
            return false;
        return view instanceof ViewGroup && !(view instanceof RecyclerView);
    }

    public static Object getTag(Object view, int id) {
        if (view != null)
            if (view instanceof View)
                return ((View) view).getTag(id);
            else if (view instanceof Tab) {
                Tab tab = (Tab) view;
                SparseArray<Object> tags = (SparseArray<Object>) tab.tag;
                if (tags != null)
                    return tags.get(id);
            } else if (view instanceof MenuItem) {
                MenuItem item = (MenuItem) view;
                SparseArray<Object> tags = (SparseArray<Object>) item.tag;
                if (tags != null)
                    return tags.get(id);
            }
        return null;
    }

    public static void setTag(Object view, int id, Object value) {
        if (view != null)
            if (view instanceof View)
                ((View) view).setTag(id, value);
            else if (view instanceof Tab) {
                Tab tab = (Tab) view;
                SparseArray<Object> tags = (SparseArray<Object>) tab.tag;
                if (tags == null) {
                    tags = new SparseArray<>();
                    tab.tag = tags;
                }
                tags.put(id, value);
            } else if (view instanceof MenuItem) {
                MenuItem item = (MenuItem) view;
                SparseArray<Object> tags = (SparseArray<Object>) item.tag;
                if (tags == null) {
                    tags = new SparseArray<>();
                    item.tag = tags;
                }
                tags.put(id, value);
            }
    }

    public static ViewGroup.LayoutParams getLayoutParams(Object view) {
        if (view != null && view instanceof View)
            return ((View) view).getLayoutParams();
        return null;
    }

    public static void setLayoutParams(Object view, ViewGroup.LayoutParams layoutParams) {
        if (view != null && view instanceof View)
            ((View) view).setLayoutParams(layoutParams);
    }

    public static Object getMinimumWidth(Object view) {
        if (view != null && view instanceof View)
            return ((View) view).getMinimumWidth();
        return null;
    }

    public static Object getMinimumHeight(Object view) {
        if (view != null && view instanceof View)
            return ((View) view).getMinimumHeight();
        return null;
    }

    public static void setMinimumWidth(Object view, int v) {
        if (view != null && view instanceof View) {
            ((View) view).setMinimumWidth(v);
            if (view instanceof TextView)
                ((TextView) view).setMinWidth(v);
        }
    }

    public static void setMinimumHeight(Object view, int v) {
        if (view != null && view instanceof View) {
            ((View) view).setMinimumHeight(v);
            if (view instanceof TextView)
                ((TextView) view).setMinHeight(v);
        }
    }

    public static float getAlpha(Object view) {
        if (view != null && view instanceof View)
            return ViewCompat.getAlpha((View) view);
        return 1.0f;
    }

    public static void setAlpha(Object view, float v) {
        if (view != null && view instanceof View)
            ViewCompat.setAlpha((View) view, v);
    }

    public static int getPaddingLeft(Object view) {
        if (view != null && view instanceof View)
            return ((View) view).getPaddingLeft();
        return 0;
    }

    public static int getPaddingRight(Object view) {
        if (view != null && view instanceof View)
            return ((View) view).getPaddingRight();
        return 0;
    }

    public static int getPaddingTop(Object view) {
        if (view != null && view instanceof View)
            return ((View) view).getPaddingTop();
        return 0;
    }

    public static int getPaddingBottom(Object view) {
        if (view != null && view instanceof View)
            return ((View) view).getPaddingBottom();
        return 0;
    }

    public static void setPaddingLeft(Object view, int p) {
        if (view != null && view instanceof View) {
            View v = (View) view;
            v.setPadding(p, v.getPaddingTop(), v.getPaddingRight(), v.getPaddingBottom());
            v.requestLayout();
        }
    }

    public static void setPaddingTop(Object view, int p) {
        if (view != null && view instanceof View) {
            View v = (View) view;
            v.setPadding(v.getPaddingLeft(), p, v.getPaddingRight(), v.getPaddingBottom());
            v.requestLayout();
        }
    }

    public static void setPaddingRight(Object view, int p) {
        if (view != null && view instanceof View) {
            View v = (View) view;
            v.setPadding(v.getPaddingLeft(), v.getPaddingTop(), p, v.getPaddingBottom());
            v.requestLayout();
        }
    }

    public static void setPaddingBottom(Object view, int p) {
        if (view != null && view instanceof View) {
            View v = (View) view;
            v.setPadding(v.getPaddingLeft(), v.getPaddingTop(), v.getPaddingRight(), p);
            v.requestLayout();
        }
    }

    public static void setOnClickListener(Object view, final Runnable runnable) {
        if (view != null)
            if (view instanceof Toolbar) {

                Toolbar toolbar = (Toolbar) view;
                if (runnable != null)
                    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            runnable.run();
                        }
                    });
                else
                    toolbar.setNavigationOnClickListener(null);

            } else if (view instanceof MenuItem) {

                android.view.MenuItem menuItem = findRealMenuItem((MenuItem) view);
                if (menuItem != null)
                    if (runnable != null)
                        menuItem.setOnMenuItemClickListener(new android.view.MenuItem.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(android.view.MenuItem item) {
                                runnable.run();
                                return true;
                            }
                        });
                    else
                        menuItem.setOnMenuItemClickListener(null);

            } else if (view instanceof View) {

                if (runnable == null)
                    ((View) view).setOnClickListener(null);
                else
                    ((View) view).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            runnable.run();
                        }
                    });
                ((View) view).setClickable(runnable != null);

            }
    }

    public static void setOnLongClickListener(Object view, final Runnable runnable) {
        if (view != null)
            if (view instanceof View && !(view instanceof Toolbar)) {

                if (runnable == null)
                    ((View) view).setOnLongClickListener(null);
                else
                    ((View) view).setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            runnable.run();
                            return true;
                        }
                    });
                ((View) view).setLongClickable(runnable != null);

            }
    }

    public static void getRelativeRect(Object root, Object view, Rect outRect) {
        outRect.setEmpty();
        outRect.right = Utils.getWidth(view);
        outRect.bottom = Utils.getHeight(view);
        getRelativeRectDeep(root, view, outRect);
    }

    private static void getRelativeRectDeep(Object root, Object view, Rect outRect) {
        if (root == view)
            return;
        Object parent = Utils.getParent(view);
        if (parent != null) {
            outRect.offset(Utils.getLeft(view), Utils.getTop(view));
            outRect.offset(-Utils.getScrollX(parent), -Utils.getScrollY(parent));
            if (parent != root)
                getRelativeRectDeep(root, parent, outRect);
        }
    }

    public static boolean isVisibleInDesignTime(Object parent, Object view) {
        if (parent instanceof ViewPager) {
            int idx = indexOfChild(view);
            ViewPager viewPager = (ViewPager) parent;
            return idx == viewPager.getCurrentItem();
        } else if (parent instanceof ViewAnimator) {
            int idx = indexOfChild(view);
            ViewAnimator viewAnimator = (ViewAnimator) parent;
            return idx == viewAnimator.getDisplayedChild();
        }
        return true;
    }

    public static int getVisibility(Object view) {
        if (view != null)
            if (view instanceof View)
                return ((View) view).getVisibility();
        return View.VISIBLE;
    }

    public static void setVisibility(Object view, int visibility) {
        if (view != null)
            if (view instanceof View)
                ((View) view).setVisibility(visibility);
    }

    public static Drawable getBackground(Object view) {
        if (view != null)
            if (view instanceof View)
                return ((View) view).getBackground();
        return null;
    }

    public static void setBackground(Object view, Drawable drawable) {
        if (view != null)
            if (view instanceof View)
                ((View) view).setBackground(drawable);
    }

    public static void invalidate(Object view) {
        if (view != null)
            if (view instanceof View)
                ((View) view).invalidate();
    }

    public static boolean getClipToPadding(Object view) {
        if (view instanceof ViewGroup)
            return ((ViewGroup) view).getClipToPadding();
        return false;
    }

    public static void setClipToPadding(Object view, boolean value) {
        if (view instanceof ViewGroup)
            ((ViewGroup) view).setClipToPadding(value);
    }

    public static void setClipToOutline(Object view, boolean b) {
        if (view != null)
            if (view instanceof View)
                ((View) view).setClipToOutline(b);
    }

    public static ViewOutlineProvider getOutlineProvider(Object view) {
        if (view != null)
            if (view instanceof View)
                return ((View) view).getOutlineProvider();
        return null;
    }

    public static void setOutlineProvider(Object view, ViewOutlineProvider viewOutlineProvider) {
        if (view != null)
            if (view instanceof View)
                ((View) view).setOutlineProvider(viewOutlineProvider);
    }

    public static void requestLayout(Object view) {
        if (view != null)
            if (view instanceof View)
                ((View) view).requestLayout();
            else if (view instanceof Tab) {
                View v = getViewForTab((Tab) view);
                if (v != null)
                    v.requestLayout();
            } else if (view instanceof MenuItem) {
                View v = getViewForMenuItem((MenuItem) view);
                if (v != null)
                    v.requestLayout();
            }
    }

    public static void invalidateDrawable(Object view, Drawable drawable) {
        if (view != null)
            if (view instanceof View)
                ((View) view).invalidateDrawable(drawable);
            else if (view instanceof Tab) {
                View v = getViewForTab((Tab) view);
                if (v != null)
                    v.invalidateDrawable(drawable);
            } else if (view instanceof MenuItem) {
                View v = getViewForMenuItem((MenuItem) view);
                if (v != null)
                    v.invalidateDrawable(drawable);
            }
    }

    public static void invalidateOutline(Object view) {
        if (view != null)
            if (view instanceof View)
                ((View) view).invalidateOutline();
    }

    private static View getViewForTab(Tab tab) {
        if (tab == null)
            return null;

        Object parent = getParent(tab);
        if (parent == null || !(parent instanceof TabLayout))
            return null;

        final int idx = indexOfChild(tab);

        TabLayout tabLayout = (TabLayout) parent;

        if (tabLayout.getChildCount() == 0)
            return null;

        ViewGroup viewGroup = (ViewGroup) tabLayout.getChildAt(0);
        if (0 <= idx && idx < viewGroup.getChildCount())
            return viewGroup.getChildAt(idx);

        return null;
    }

    public static ImageView getMoreIconViewForToolbar(Toolbar toolbar) {
        if (toolbar.getChildCount() == 0)
            return null;

        ViewGroup buttonsViewGroup = null;

        for (int i = 0; i < toolbar.getChildCount(); i++) {
            View child = toolbar.getChildAt(i);
            if (child instanceof ActionMenuView) {
                buttonsViewGroup = (ViewGroup) child;
                break;
            }
        }

        if (buttonsViewGroup != null && buttonsViewGroup.getChildCount() > 0) {
            View lastView = buttonsViewGroup.getChildAt(buttonsViewGroup.getChildCount() - 1);
            if (lastView instanceof ImageView)
                return (ImageView) lastView;
        }

        return null;
    }

    public static View getCustomViewForToolbar(Toolbar toolbar) {
        if (toolbar.getChildCount() == 0)
            return null;

        for (int i = 0; i < toolbar.getChildCount(); i++) {
            View child = toolbar.getChildAt(i);
            if (child.getClass().equals(LinearLayout.class))
                return child;
        }

        return null;
    }

    public static ImageView getNavIconViewForToolbar(Toolbar toolbar) {
        if (toolbar.getChildCount() == 0)
            return null;

        View firstView = toolbar.getChildAt(0);
        if (firstView != null && firstView instanceof ImageButton)
            return (ImageView) firstView;

        return null;
    }

    public static View getViewForMenuItem(MenuItem menuItem) {
        if (menuItem == null)
            return null;

        Object parent = getParent(menuItem);
        if (parent == null || !(parent instanceof Toolbar))
            return null;

        Toolbar toolbar = (Toolbar) parent;

        if (toolbar.getChildCount() == 0)
            return null;

        ViewGroup buttonsViewGroup = null;

        for (int i = 0; i < toolbar.getChildCount(); i++) {
            View child = toolbar.getChildAt(i);
            if (child instanceof LinearLayoutCompat) {
                buttonsViewGroup = (ViewGroup) child;
                break;
            }
        }

        if (buttonsViewGroup != null) {
            final int idx = indexOfChild(menuItem);

            if (0 <= idx && idx < buttonsViewGroup.getChildCount())
                return buttonsViewGroup.getChildAt(idx);
            else if (buttonsViewGroup.getChildCount() > 0)
                return buttonsViewGroup.getChildAt(buttonsViewGroup.getChildCount() - 1);
        }

        return null;
    }

    public static View getViewFor(Object view) {
        if (view != null)
            if (view instanceof View)
                return (View) view;
            else if (view instanceof Tab)
                return getViewForTab((Tab) view);
            else if (view instanceof MenuItem)
                return getViewForMenuItem((MenuItem) view);
        return null;
    }

    public static int getWidth(Object view) {
        View v = getViewFor(view);
        if (v != null)
            return v.getWidth();
        return 0;
    }

    public static int getHeight(Object view) {
        View v = getViewFor(view);
        if (v != null)
            return v.getHeight();
        return 0;
    }

    public static int getLeft(Object view) {
        if (view != null)
            if (view instanceof View)
                return ((View) view).getLeft();
            else if (view instanceof Tab) {
                View tabView = getViewForTab((Tab) view);
                if (tabView != null)
                    return tabView.getLeft();
            } else if (view instanceof MenuItem) {
                View menuItemView = getViewForMenuItem((MenuItem) view);
                if (menuItemView != null)
                    return menuItemView.getLeft() + ((View) menuItemView.getParent()).getLeft();
            }
        return 0;
    }

    public static int getTop(Object view) {
        if (view != null)
            if (view instanceof View)
                return ((View) view).getTop();
            else if (view instanceof Tab) {
                View tabView = getViewForTab((Tab) view);
                if (tabView != null)
                    return tabView.getTop();
            } else if (view instanceof MenuItem) {
                View menuItemView = getViewForMenuItem((MenuItem) view);
                if (menuItemView != null)
                    return menuItemView.getTop() + ((View) menuItemView.getParent()).getTop();
            }
        return 0;
    }

    public static int getScrollX(Object view) {
        if (view != null)
            if (view instanceof View)
                return ((View) view).getScrollX();
        return 0;
    }

    public static int getScrollY(Object view) {
        if (view != null)
            if (view instanceof View)
                return ((View) view).getScrollY();
        return 0;
    }

    public static TabLayout.Tab findRealTabForTab(Tab tab) {
        Object parent = getParent(tab);
        if (parent != null && parent instanceof TabLayout) {
            TabLayout tabLayout = (TabLayout) parent;
            int idx = indexOfChild(tab);
            if (0 <= idx && idx < tabLayout.getTabCount())
                return tabLayout.getTabAt(idx);
        }
        return null;
    }

    public static void activateChild(Object view) {
        if (view == null)
            return;
        Object parent = Utils.getParent(view);
        if (parent != null)
            if (parent instanceof TabLayout) {
                if (view instanceof Tab) {
                    TabLayout.Tab tab = findRealTabForTab((Tab) view);
                    if (tab != null)
                        tab.select();
                }
            } else if (view instanceof View) {
                View v = (View) view;
                ScrollView scrollView = findScrollViewInParents(view);
                if (scrollView != null) {
                    if (v != scrollView) {
                        Rect rect = new Rect();
                        v.getDrawingRect(rect);
                        scrollView.offsetDescendantRectToMyCoords(v, rect);
                        int deltaY = computeScrollDeltaToGetChildRectOnScreen(scrollView, rect);
                        if (deltaY != 0)
                            scrollView.smoothScrollBy(0, deltaY);
                    } else {
                        scrollView.smoothScrollTo(0, 0);
                    }
                }

                ViewAnimator viewAnimator = Utils.findViewAnimatorInParents(view);
                if (viewAnimator != null) {
                    int idx = Utils.indexOfChild(view);
                    if (idx != -1 && viewAnimator.getDisplayedChild() != idx)
                        viewAnimator.setDisplayedChild(idx);

                }

                ViewPager viewPager = Utils.findViewPagerInParents(view);
                if (viewPager != null) {
                    int idx = Utils.indexOfChild(view);
                    if (idx != -1 && idx != viewPager.getCurrentItem())
                        viewPager.setCurrentItem(idx, false);
                }
            }
    }

    public static android.view.MenuItem findRealMenuItem(MenuItem menuItem) {
        Object parent = getParent(menuItem);
        if (parent != null && parent instanceof Toolbar) {
            Toolbar toolbar = (Toolbar) parent;
            return toolbar.getMenu().findItem(menuItem.id);
        }
        return null;
    }

    private static int computeScrollDeltaToGetChildRectOnScreen(ScrollView scrollView, Rect rect) {
        if (scrollView.getChildCount() == 0)
            return 0;

        int height = scrollView.getHeight();
        int screenTop = scrollView.getScrollY();
        int screenBottom = screenTop + height;

        int fadingEdge = scrollView.getVerticalFadingEdgeLength();

        // leave room for top fading edge as long as rect isn't at very top
        if (rect.top > 0) {
            screenTop += fadingEdge;
        }

        // leave room for bottom fading edge as long as rect isn't at very bottom
        if (rect.bottom < scrollView.getChildAt(0).getHeight()) {
            screenBottom -= fadingEdge;
        }

        int scrollYDelta = 0;

        if (rect.bottom > screenBottom && rect.top > screenTop) {
            // need to move down to get it in view: move down just enough so
            // that the entire rectangle is in view (or at least the first
            // screen size chunk).

            if (rect.height() > height) {
                // just enough to get screen size chunk on
                scrollYDelta += (rect.top - screenTop);
            } else {
                // get entire rect at bottom of screen
                scrollYDelta += (rect.bottom - screenBottom);
            }

            // make sure we aren't scrolling beyond the end of our content
            int bottom = scrollView.getChildAt(0).getBottom();
            int distanceToBottom = bottom - screenBottom;
            scrollYDelta = Math.min(scrollYDelta, distanceToBottom);

        } else if (rect.top < screenTop && rect.bottom < screenBottom) {
            // need to move up to get it in view: move up just enough so that
            // entire rectangle is in view (or at least the first screen
            // size chunk of it).

            if (rect.height() > height) {
                // screen size chunk
                scrollYDelta -= (screenBottom - rect.bottom);
            } else {
                // entire rect at top
                scrollYDelta -= (screenTop - rect.top);
            }

            // make sure we aren't scrolling any further than the top our content
            scrollYDelta = Math.max(scrollYDelta, -scrollView.getScrollY());
        } else if (rect.top < screenTop) {
            scrollYDelta -= (screenTop - rect.top);
            scrollYDelta = Math.max(scrollYDelta, -scrollView.getScrollY());
        }
        return scrollYDelta;
    }


    public interface ViewIdGenerator {
        int getLastViewIndex();

        void incViewIndex();

        Context getContext();
    }

    public static String ensureViewId(Object view, ViewIdGenerator viewIdGenerator, boolean force) {
        String id = PropModel.VIEW_ID.getPropAsString(view);
        if (id == null || force) {

            String baseId;

            PropLayoutValue layoutValue = PropModel.CUSTOM_LAYOUT.getPropAs(view, PropLayoutValue.class);
            if (layoutValue == null)
                baseId = view.getClass().getSimpleName();
            else
                baseId = ProjectServiceFactory.getInstace(viewIdGenerator.getContext()).formatLayoutName(layoutValue.getProjectId(), layoutValue.getLayoutId());

            id = baseId + "_" + viewIdGenerator.getLastViewIndex();
            viewIdGenerator.incViewIndex();
            PropModel.VIEW_ID.setProp(view, id);
        }
        return id;
    }

    public static String getCaptionForBreadCrumb(Object view) {
        String id = PropModel.VIEW_ID.getPropAsString(view);
        String name = id == null ? "?" + view.getClass().getSimpleName() + "?" : id;

        String suffix = "";

        if (view instanceof LinearLayout) {
            LinearLayout linearLayout = (LinearLayout) view;
            switch (linearLayout.getOrientation()) {
                case LinearLayout.HORIZONTAL:
                    suffix = " H";
                    break;
                case LinearLayout.VERTICAL:
                    suffix = " V";
                    break;
            }
        }

        return name + suffix;
    }

    public static boolean isRootView(Object view) {
        if (view == null)
            return true;
        if (view instanceof RecyclerView)
            return true;
        if (PropModel.VIEW_ID.getPropAsString(view) == null)
            return true;
//        if (view instanceof DesignTimeLayout)
//            return true;
//        if (view instanceof View && ((View) view).getId() == R.id.container)
//            return true;
        return false;
    }

    public static Object getRootView(Object view) {
        Object v = view;
        while (true) {
            Object parent = getParent(v);
            if (parent == null)
                break;
            if (isRootView(parent))
                return v;
            v = parent;
        }
        return v;
    }

    public static String buildViewIdPath(Object view, ViewIdGenerator viewIdGenerator) {
        return buildViewIdPath(view, viewIdGenerator, false);
    }

    public static String buildViewIdPath(Object view, ViewIdGenerator viewIdGenerator, boolean force) {
        if (view == null)
            return null;

        StringBuilder sb = new StringBuilder();

        final Object root = getRootView(view);

        while (true) {
            String viewId = ensureViewId(view, viewIdGenerator, force);

            if (sb.length() != 0)
                sb.insert(0, "/");
            sb.insert(0, viewId);

            if (view == root)
                break;

            view = getParent(view);
        }

        return sb.toString();
    }

    public static Object findViewByViewIdPath(Object viewToStart, String path) {
        if (viewToStart == null || path == null)
            return null;
        String[] parts = path.split("/");
        if (parts.length == 0)
            return null;

        if (!parts[0].equals(PropModel.VIEW_ID.getPropAsString(viewToStart)))
            return null;

        if (parts.length == 1)
            return viewToStart;

        Object v = viewToStart;

        for (int i = 1; i < parts.length; i++) {
            v = findViewWithViewIdTag(v, parts[i]);
            if (v == null)
                return null;
            if (i == parts.length - 1)
                return v;
        }
        return null;
    }

    public static Object findViewWithViewIdTag(Object parent, final String viewId) {
        if (viewId == null)
            return null;
        if (parent == null)
            return null;

        return iterateChildren(parent, new ViewChildrenIterator() {
            @Override
            public Object processChild(Object parent, Object child, int position) {
                if (viewId.equals(PropModel.VIEW_ID.getPropAsString(child)))
                    return child;
                return null;
            }
        });
    }

    public static void fixViewIdPaths(final PropModel propModel, Object view, final String oldId, final String newIdPath) {
        if (oldId == null || newIdPath == null)
            return;

        if (StringUtils.equals(oldId, newIdPath))
            return;

        switch (propModel) {
            case ON_CLICK:
            case ON_LONG_CLICK: {
                PropEventValue propEventValue = propModel.getPropAs(view, PropEventValue.class);

                if (propEventValue != null && propEventValue instanceof PropEventChangePropValues) {
                    PropEventChangePropValues propEventChangePropValues = (PropEventChangePropValues) propEventValue;

                    if (propEventChangePropValues.getProps() != null && propEventChangePropValues.getProps().size() > 0) {
                        boolean changed = false;
                        for (PropEventChangePropValue value : propEventChangePropValues.getProps()) {
                            if (value.viewIdPath != null) {
                                value.viewIdPath = fixViewIdPath(value.viewIdPath, oldId, newIdPath);
                                changed = true;
                            }
                        }
                        if (changed)
                            propModel.setProp(view, propEventValue, PropModel.PropModifyAction.FORCE_MODIFY);
                    }
                }
            }
            break;

            case TAB_LAYOUT_VIEW_PAGER_REF: {
                String viewPagerRef = propModel.getPropAsString(view);
                if (viewPagerRef != null)
                    propModel.setProp(view, fixViewIdPath(viewPagerRef, oldId, newIdPath), PropModel.PropModifyAction.FORCE_MODIFY);
            }
            break;
        }


        iterateChildren(view, new ViewChildrenIterator() {
            @Override
            public Object processChild(Object parent, Object child, int position) {
                fixViewIdPaths(propModel, child, oldId, newIdPath);
                return null;
            }
        });
    }

    private static String fixViewIdPath(String viewIdPath, String oldId, String newIdPath) {
        if (viewIdPath == null)
            return null;

        int idx = viewIdPath.indexOf(oldId);
        if (idx != -1) {
            idx += oldId.length();
            return newIdPath + viewIdPath.substring(idx);
        }
        return viewIdPath;
    }

    public static Object createView(Context context, Class viewClass, Object parent) {
        if (View.class.isAssignableFrom(viewClass)) {
            try {
                Constructor constructor = viewClass.getConstructor(Context.class);
                return constructor.newInstance(context);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (Tab.class.isAssignableFrom(viewClass) && parent != null && parent instanceof TabLayout) {
            return new Tab();
        } else if (MenuItem.class.isAssignableFrom(viewClass) && parent != null && parent instanceof Toolbar) {
            return new MenuItem();
        }
        return null;
    }

    public static String genUUID() {
        return UUID.randomUUID().toString();
    }

    public static LayoutDescriptor loadLayoutDescriptor(Context context, String layoutDescriptorString, String projectId, String layoutId) {
        LayoutDescriptor layoutDescriptor = null;
        if (layoutDescriptorString != null)
            layoutDescriptor = LayoutDescriptor.fromString(layoutDescriptorString);
        else if (projectId != null && layoutId != null)
            layoutDescriptor = ProjectServiceFactory.getInstace(context).loadLayout(projectId, layoutId);
        return layoutDescriptor;
    }

    private static Boolean premium;

    public static boolean isPremium(Context context) {
        if (com.crane.mockapp.core.BuildConfig.DEBUG)
            return true;
        if (premium == null) {
            SharedPreferences sp = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
            premium = sp.getBoolean("premium", false);
        }
        return premium;
    }

    public static void makePremium(Context context) {
        SharedPreferences.Editor spe = context.getSharedPreferences("settings", Context.MODE_PRIVATE).edit();
        spe.putBoolean("premium", true);
        spe.apply();
        premium = true;
    }
}
