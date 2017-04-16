package com.crane.mockapp.core.model.layouts;

import android.graphics.Bitmap;

import com.crane.mockapp.core.OperationCallback;

import java.util.List;

/**
 * Created by crane2002 on 1/1/2017.
 */

public interface ProjectService {

    interface ProjectFilter {
        boolean accept(ProjectModel projectModel);
    }

    interface LayoutFilter {
        boolean accept(LayoutModel layoutModel);
    }

    LayoutFilter LAYOUT_FILTER_ALL = new LayoutFilter() {
        @Override
        public boolean accept(LayoutModel layoutModel) {
            return true;
        }
    };

    ProjectFilter PROJECT_FILTER_ALL = new ProjectFilter() {
        @Override
        public boolean accept(ProjectModel layoutModel) {
            return true;
        }
    };

    class ProjectModelsResult {
        public List<ProjectModel> localProjectModels;
        public List<ProjectModel> internalProjectModels;
    }

    String formatLayoutName(String projectId, String layoutId);

    String formatProjectName(String projectId);

    String formatProjectPath(String projectId);

    String formatImageName(String imageFileName);

    boolean isProjectReadOnly(String projectId);

    int getLocalProjectCount();

    int getLocalLayoutCount(String projectId);

    void createLocalProject(OperationCallback<ProjectModel> callback);

    void createLocalProject(String projectName, OperationCallback<ProjectModel> callback);

    void findProjectModels(ProjectFilter filter, OperationCallback<ProjectModelsResult> callback);

    void findInternalProjectModels(OperationCallback<List<ProjectModel>> callback);

    List<ProjectModel> findInternalProjectModels();

    void findLayoutModels(LayoutFilter filter, String projectId, OperationCallback<List<LayoutModel>> callback);

    List<LayoutModel> findLayoutModels(LayoutFilter filter, String projectId);

    void openProject(LayoutFilter filter, String projectId, OperationCallback<List<LayoutModel>> callback);

    void deleteProject(String projectId, OperationCallback<Void> callback);

    void renameProject(String projectId, String newProjectName, OperationCallback<Void> callback);

    void deleteLayout(String projectId, String layoutId, OperationCallback<Void> callback);

    void renameLayout(String projectId, String layoutId, String newLayoutName, OperationCallback<Void> callback);

    void duplicateLayout(String projectId, String layoutId, OperationCallback<LayoutModel> callback);

    String getMainLayoutId(String projectId);

    void setMainLayoutId(String projectId, String layoutId, OperationCallback<Void> callback);

    void loadLayout(String projectId, String layoutId, OperationCallback<LayoutDescriptor> callback);

    LayoutDescriptor loadLayout(String projectId, String layoutId);

    LayoutDescriptor loadLayoutByName(String projectName, String layoutName);

    void loadLayoutAsString(String projectId, String layoutId, OperationCallback<String> callback);

    void saveLayout(String projectId, String layoutName, LayoutDescriptor layoutDescriptor, OperationCallback<LayoutModel> callback);

    Bitmap getLayoutThumbnail(String projectId, String layoutId);

    Bitmap getLayoutThumbnail1(String projectId);

    Bitmap getLayoutThumbnail2(String projectId);

    Bitmap getLayoutThumbnail3(String projectId);

    Bitmap getLayoutThumbnail4(String projectId);

    Bitmap getLayoutScreenshot(String projectId, String layoutId);

    Bitmap loadImage(String projectId, String imageFileName, int reqWidth, int reqHeight);

    String saveImage(String projectId, String pathToImageFile);

    void makeZip(String projectId, OperationCallback<String> callback);

    void importZip(String pathToZipFile, OperationCallback<ProjectModel> callback);

    void previewLayout(String projectId, String layoutId);

    void previewMainLayout(String projectId);

    void generatePdf(String projectId, String layoutId, OperationCallback<String> callback);
}
