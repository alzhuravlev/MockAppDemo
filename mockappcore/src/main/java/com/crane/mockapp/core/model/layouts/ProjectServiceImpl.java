package com.crane.mockapp.core.model.layouts;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Environment;
import android.text.TextUtils;

import com.crane.mockapp.core.Arguments;
import com.crane.mockapp.core.BitmapUtils;
import com.crane.mockapp.core.FileUtils;
import com.crane.mockapp.core.ImageProvider;
import com.crane.mockapp.core.JsonUtils;
import com.crane.mockapp.core.PreviewActivity;
import com.crane.mockapp.core.R;
import com.crane.mockapp.core.StringUtils;
import com.crane.mockapp.core.Utils;
import com.crane.mockapp.core.OperationCallback;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.os.Environment.MEDIA_MOUNTED;
import static com.crane.mockapp.core.JsonUtils.fromInputStream;
import static com.crane.mockapp.core.JsonUtils.fromString;

/**
 * Created by crane2002 on 1/1/2017.
 */

class ProjectServiceImpl implements ProjectService {

    private Context context;

    private Projects projectsLocal;
    private Projects projectsInternal;

    private Map<String, Project> projectLocalMap = new HashMap<>();
    private Map<String, Project> projectInternalMap = new HashMap<>();

    ProjectServiceImpl(Context context) {
        this.context = context;
    }

    private File getLocalStorageFolder() {
        if (!MEDIA_MOUNTED.equals(Environment.getExternalStorageState()))
            return null;

        File folderFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), LOCAL_STORAGE_FOLDER);

        if (!folderFile.exists())
            if (!folderFile.mkdirs())
                return null;
        return folderFile;
    }

    private boolean deleteFileOrFolder(File file) {
        if (file == null)
            return false;
        if (file.isDirectory()) {
            boolean success = true;
            File[] children = file.listFiles();
            if (children != null && children.length > 0)
                for (File child : children)
                    success = success && deleteFileOrFolder(child);
            return success && file.delete();
        } else
            return file.delete();
    }

    private File getLocalTmpFolder() {
        File localStorageFolder = getLocalStorageFolder();
        if (localStorageFolder == null)
            return null;

        File tmpFolder = new File(localStorageFolder, TMP_FOLDER);

        if (!tmpFolder.exists())
            if (!tmpFolder.mkdirs())
                return null;

        return tmpFolder;
    }

    private File getLocalProjectFolderByName(String projectName) {
        return getLocalProjectFolderByName(projectName, true);
    }

    private File getLocalProjectFolderByName(String projectName, boolean create) {
        File localStorageFolder = getLocalStorageFolder();
        if (localStorageFolder == null)
            return null;

        File projectFolder = new File(localStorageFolder, projectName);

        if (create)
            if (!projectFolder.exists())
                if (!projectFolder.mkdirs())
                    return null;

        return projectFolder;
    }

    private File getLocalProjectFolderById(String projectId) {
        return getLocalProjectFolderById(projectId, true);
    }

    private File getLocalProjectFolderById(String projectId, boolean create) {
        Projects projects = ensureLocalProjects();
        ProjectModel projectModel = projects.findById(projectId);
        if (projectModel == null)
            return null;

        return getLocalProjectFolderByName(projectModel.getName(), create);
    }

    private static final String LOCAL_STORAGE_FOLDER = "MockApp";
    private static final String TMP_FOLDER = "tmp";
    private static final String IMAGES_FOLDER = "images";
    private static final String INTERNAL_PROJECTS_FOLDER = "projects";
    private static final String PROJECTS_FILE_NAME = "projects.json";
    private static final String PROJECT_FILE_NAME = "project.json";

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Projects {
        @JsonProperty
        List<ProjectModel> projectModels;

        @JsonCreator
        Projects(@JsonProperty("projectModels") List<ProjectModel> projectModels) {
            this.projectModels = projectModels;
        }

        ProjectModel findById(String projectId) {
            if (projectModels == null)
                return null;
            for (ProjectModel projectModel : projectModels)
                if (StringUtils.equals(projectModel.getId(), projectId))
                    return projectModel;
            return null;
        }

        ProjectModel findByName(String projectName) {
            if (projectModels == null)
                return null;
            for (ProjectModel projectModel : projectModels)
                if (StringUtils.equals(projectModel.getName(), projectName))
                    return projectModel;
            return null;
        }

        void removeById(String projectId) {
            if (projectModels == null)
                return;
            for (ProjectModel projectModel : projectModels) {
                if (StringUtils.equals(projectModel.getId(), projectId)) {
                    projectModels.remove(projectModel);
                    break;
                }
            }
        }

        void removeByName(String projectName) {
            if (projectModels == null)
                return;
            for (ProjectModel projectModel : projectModels) {
                if (StringUtils.equals(projectModel.getId(), projectName)) {
                    projectModels.remove(projectModel);
                    break;
                }
            }
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Project {
        @JsonProperty
        List<LayoutModel> layoutModels;

        @JsonProperty
        String mainLayoutId;

        @JsonProperty
        String id;

        @JsonCreator
        Project(@JsonProperty("layoutModels") List<LayoutModel> layoutModels,
                @JsonProperty("mainLayoutId") String mainLayoutId,
                @JsonProperty("id") String id) {
            this.layoutModels = layoutModels;
            this.mainLayoutId = mainLayoutId;
            this.id = id;
        }

        LayoutModel findById(String layoutId) {
            if (layoutModels != null)
                for (LayoutModel layoutModel : layoutModels)
                    if (StringUtils.equals(layoutModel.getId(), layoutId))
                        return layoutModel;
            return null;
        }

        LayoutModel findByName(String layoutName) {
            if (layoutModels != null)
                for (LayoutModel layoutModel : layoutModels)
                    if (StringUtils.equals(layoutModel.getName(), layoutName))
                        return layoutModel;
            return null;
        }

        void removeById(String layoutId) {
            if (layoutModels == null)
                return;
            for (LayoutModel layoutModel : layoutModels) {
                if (StringUtils.equals(layoutModel.getId(), layoutId)) {
                    layoutModels.remove(layoutModel);
                    break;
                }
            }
        }
    }

    private Projects ensureInternalProjects() {
        if (projectsInternal == null) {
            AssetManager assetManager = context.getAssets();
            try {
                try (InputStream inputStream = assetManager.open(INTERNAL_PROJECTS_FOLDER + "/" + PROJECTS_FILE_NAME)) {
                    projectsInternal = fromInputStream(inputStream, Projects.class);

                    if (projectsInternal != null && projectsInternal.projectModels != null)
                        for (ProjectModel projectModel : projectsInternal.projectModels)
                            projectModel.projectType = ProjectModel.ProjectType.INTERNAL;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (projectsInternal == null)
            projectsInternal = new Projects(new ArrayList<ProjectModel>());

        return projectsInternal;
    }

    private Projects ensureLocalProjects() {
        if (projectsLocal == null) {
            File localStorageFolder = getLocalStorageFolder();
            if (localStorageFolder != null) {
                File projectsFile = new File(localStorageFolder, PROJECTS_FILE_NAME);
                if (projectsFile.exists())
                    projectsLocal = JsonUtils.fromFile(projectsFile, Projects.class);
            }
        }
        if (projectsLocal == null)
            projectsLocal = new Projects(new ArrayList<ProjectModel>());
        return projectsLocal;
    }

    private Project ensureAnyProject(String projectId) {
        Projects localProjects = ensureLocalProjects();
        ProjectModel projectModel = localProjects.findById(projectId);
        if (projectModel != null)
            return ensureLocalProject(projectId);
        return ensureInternalProject(projectId);
    }

    private Project ensureLocalProject(String projectId) {
        Project project = projectLocalMap.get(projectId);
        if (project == null) {

            File projectFolder = getLocalProjectFolderById(projectId, false);
            if (projectFolder != null && projectFolder.exists()) {
                File projectFile = new File(projectFolder, PROJECT_FILE_NAME);
                if (projectFile.exists())
                    project = JsonUtils.fromFile(projectFile, Project.class);
            }

            if (project == null)
                project = new Project(new ArrayList<LayoutModel>(), null, null);

            project.id = projectId;
            projectLocalMap.put(projectId, project);
        }
        return project;
    }

    private Project ensureInternalProject(String projectId) {
        Project project = projectInternalMap.get(projectId);
        if (project == null) {

            Projects projects = ensureInternalProjects();
            ProjectModel projectModel = projects.findById(projectId);

            if (projectModel != null) {
                AssetManager assetManager = context.getAssets();
                try {
                    try (InputStream inputStream = assetManager.open(INTERNAL_PROJECTS_FOLDER + "/" + projectModel.name + "/" + PROJECT_FILE_NAME)) {
                        project = JsonUtils.fromInputStream(inputStream, Project.class);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (project == null)
                project = new Project(new ArrayList<LayoutModel>(), null, null);

            project.id = projectId;
            projectInternalMap.put(projectId, project);
        }
        return project;
    }

    private void saveProjects(Projects projects) {
        saveProjects(projects, false);
    }

    private static final Comparator<ProjectModel> PROJECTS_COMPARATOR_RECENT = new Comparator<ProjectModel>() {
        @Override
        public int compare(ProjectModel o1, ProjectModel o2) {
            if (o1.lastOpened == null && o2.lastOpened != null)
                return -1;
            if (o1.lastOpened != null && o2.lastOpened == null)
                return 1;
            if (o1.lastOpened == null)
                return 0;
            return o2.lastOpened.compareTo(o1.lastOpened);
        }
    };

    private void saveProjects(Projects projects, boolean sort) {
        File localStorageFolder = getLocalStorageFolder();
        if (localStorageFolder == null)
            return;
        File projectsFile = new File(localStorageFolder, PROJECTS_FILE_NAME);
        if (sort)
            Collections.sort(projects.projectModels, PROJECTS_COMPARATOR_RECENT);
        JsonUtils.toFile(projects, projectsFile, true);
    }

    private void saveProject(String projectId, Project project) {
        File projectFolder = getLocalProjectFolderById(projectId, false);
        if (projectFolder == null || !projectFolder.exists())
            return;
        File projectFile = new File(projectFolder, PROJECT_FILE_NAME);
        JsonUtils.toFile(project, projectFile, true);
    }

    @Override
    public String formatLayoutName(String projectId, String layoutId) {
        Project project = ensureAnyProject(projectId);
        LayoutModel layoutModel = project.findById(layoutId);
        return layoutModel == null ? "" : layoutModel.getName();
    }

    @Override
    public String formatProjectName(String projectId) {
        Projects localProjects = ensureLocalProjects();
        ProjectModel projectModel = localProjects.findById(projectId);
        if (projectModel == null) {
            Projects internalProjects = ensureInternalProjects();
            projectModel = internalProjects.findById(projectId);
        }
        return projectModel == null ? "" : projectModel.getName();
    }

    @Override
    public String formatProjectPath(String projectId) {
        Projects projects = ensureLocalProjects();
        ProjectModel projectModel = projects.findById(projectId);

        if (projectModel == null)
            return "";

        switch (projectModel.projectType) {
            case LOCAL_FILE_SYSTEM: {
                if (TextUtils.isEmpty(projectModel.getName()))
                    return "";
                File localStorageFolder = getLocalStorageFolder();
                if (localStorageFolder != null)
                    return new File(localStorageFolder, projectModel.getName()).getPath();
            }
        }
        return "";
    }

    @Override
    public String formatImageName(String imageFileName) {
        if (imageFileName == null)
            return "";

        String projectId = null;

        final int slashIdx = imageFileName.indexOf('/');
        if (slashIdx != -1) {
            projectId = imageFileName.substring(0, slashIdx);
            imageFileName = imageFileName.substring(slashIdx + 1);
        }

        String projectName = projectId == null ? "" : formatProjectName(projectId) + " / ";
        return projectName + imageFileName;
    }

    @Override
    public void findProjectModels(ProjectFilter filter, OperationCallback<ProjectModelsResult> callback) {
        ProjectModelsResult result = new ProjectModelsResult();

        result.localProjectModels = new ArrayList<>();
        result.internalProjectModels = new ArrayList<>();

        Projects localProjects = ensureLocalProjects();
        Projects internalProjects = ensureInternalProjects();

        if (localProjects != null && localProjects.projectModels != null)
            for (ProjectModel projectModel : localProjects.projectModels)
                if (filter == null || filter.accept(projectModel))
                    result.localProjectModels.add(projectModel);

        if (internalProjects != null && internalProjects.projectModels != null)
            for (ProjectModel projectModel : internalProjects.projectModels)
                if (filter == null || filter.accept(projectModel))
                    result.internalProjectModels.add(projectModel);

        callback.onSuccess(result);
    }

    @Override
    public void findInternalProjectModels(OperationCallback<List<ProjectModel>> callback) {
        Projects internalProjects = ensureInternalProjects();
        callback.onSuccess(internalProjects.projectModels);
    }

    @Override
    public List<ProjectModel> findInternalProjectModels() {
        Projects internalProjects = ensureInternalProjects();
        return internalProjects.projectModels;
    }

    private String createLayoutName(String projectId, String baseName) {
        Project project = ensureLocalProject(projectId);
        if (project == null)
            return baseName;

        int i = 0;
        while (true) {
            String name = baseName + (i == 0 ? "" : String.valueOf(i));
            LayoutModel layoutModel = project.findByName(name);
            if (layoutModel == null)
                return name;
            i++;
            if (i > 100)
                break;
        }
        return baseName;
    }

    private String createProjectName(String baseName) {
        File localStorageFolder = getLocalStorageFolder();
        if (localStorageFolder == null)
            return null;

        int i = 1;

        do {
            String name = baseName + (i == 0 ? "" : String.valueOf(i));
            File file = new File(localStorageFolder, name);
            if (!file.exists())
                return name;

            i++;
        }
        while (i < 1000);

        return null;
    }

    @Override
    public boolean isProjectReadOnly(String projectId) {
        Projects projects = ensureInternalProjects();
        return projects.findById(projectId) != null;
    }

    @Override
    public int getLocalProjectCount() {
        Projects projects = ensureLocalProjects();
        return projects.projectModels.size();
    }

    @Override
    public int getLocalLayoutCount(String projectId) {
        Project project = ensureLocalProject(projectId);
        return project.layoutModels.size();
    }

    @Override
    public void createLocalProject(OperationCallback<ProjectModel> callback) {
        createLocalProject(null, callback);
    }

    @Override
    public void createLocalProject(String projectName, OperationCallback<ProjectModel> callback) {

        if (projectName == null)
            projectName = createProjectName("Project");

        if (projectName == null) {
            callback.onFail(context.getString(R.string.error_unable_to_create_project));
            return;
        }

        File projectFolder = getLocalProjectFolderByName(projectName);
        if (projectFolder == null) {
            callback.onFail(context.getString(R.string.error_unable_to_create_project));
            return;
        }

        if (!projectFolder.exists()) {
            callback.onFail(context.getString(R.string.error_unable_to_create_project_folder_not_created));
            return;
        }

        if (!projectFolder.isDirectory()) {
            callback.onFail(context.getString(R.string.error_unable_to_create_project_already_exists, projectFolder.getName()));
            return;
        }

        String[] content = projectFolder.list();
        if (content != null && content.length > 0) {
            callback.onFail(context.getString(R.string.error_unable_to_create_project_not_empty, projectFolder.getName()));
            return;
        }

        Projects projects = ensureLocalProjects();

        ProjectModel projectModel = projects.findByName(projectName);
        if (projectModel == null) {
            projectModel = new ProjectModel(ProjectModel.ProjectType.LOCAL_FILE_SYSTEM, Utils.genUUID(), projectName, 0, new Date());
            projects.projectModels.add(projectModel);
            saveProjects(projects);
        }

        callback.onSuccess(projectModel);
    }

    @Override
    public void findLayoutModels(LayoutFilter filter, String projectId, OperationCallback<List<LayoutModel>> callback) {
        Project project = ensureAnyProject(projectId);
        List<LayoutModel> result = new ArrayList<>();
        for (LayoutModel layoutModel : project.layoutModels)
            if (filter.accept(layoutModel))
                result.add(layoutModel);
        callback.onSuccess(result);
    }

    @Override
    public List<LayoutModel> findLayoutModels(LayoutFilter filter, String projectId) {
        final ResultHolder<List<LayoutModel>> holder = new ResultHolder<>();
        findLayoutModels(filter, projectId, new OperationCallback<List<LayoutModel>>() {
            @Override
            public void onSuccess(List<LayoutModel> result) {
                holder.result = result;
            }

            @Override
            public void onFail(String message) {
            }
        });
        return holder.result;
    }

    @Override
    public void openProject(LayoutFilter filter, String projectId, OperationCallback<List<LayoutModel>> callback) {
        Projects projects = ensureLocalProjects();
        ProjectModel projectModel = projects.findById(projectId);
        if (projectModel != null) {
            projectModel.lastOpened = new Date();
            saveProjects(projects, true);
        }
        findLayoutModels(filter, projectId, callback);
    }

    @Override
    public void deleteLayout(String projectId, String layoutId, OperationCallback<Void> callback) {
        File projectFolder = getLocalProjectFolderById(projectId, false);
        if (projectFolder == null) {
            callback.onFail(context.getString(R.string.error_unable_to_delete_layout));
            return;
        }

        Project project = ensureLocalProject(projectId);
        LayoutModel layoutModel = project.findById(layoutId);
        if (layoutModel != null) {
            File layoutFile = new File(projectFolder, LayoutModel.getLayoutFileName(layoutModel.getName()));
            File layoutThumbnailFile = new File(projectFolder, LayoutModel.getBitmapFileName(layoutModel.getName(), true));
            File layoutScreenshotFile = new File(projectFolder, LayoutModel.getBitmapFileName(layoutModel.getName(), false));

            if (layoutFile.delete()) {

                Projects projects = ensureLocalProjects();
                ProjectModel projectModel = projects.findById(projectId);
                if (projectModel != null) {
                    projectModel.layoutCount = project.layoutModels.size();
                    saveProjects(projects);
                }

                layoutThumbnailFile.delete();
                layoutScreenshotFile.delete();

                project.removeById(layoutId);
                saveProject(projectId, project);
                callback.onSuccess(null);
            } else
                callback.onFail(context.getString(R.string.error_unable_to_delete_layout));

        } else
            callback.onFail(context.getString(R.string.error_unable_to_delete_layout));
    }

    @Override
    public void renameLayout(String projectId, String layoutId, String newLayoutName, OperationCallback<Void> callback) {

        File projectFolder = getLocalProjectFolderById(projectId, false);
        if (projectFolder == null) {
            callback.onFail(context.getString(R.string.error_unable_to_rename_layout));
            return;
        }

        Project project = ensureLocalProject(projectId);
        LayoutModel layoutModel = project.findById(layoutId);

        if (layoutModel == null) {
            callback.onFail(context.getString(R.string.error_unable_to_rename_layout));
            return;
        }

        File layoutFile = new File(projectFolder, LayoutModel.getLayoutFileName(layoutModel.name));
        if (!layoutFile.exists()) {
            callback.onFail(context.getString(R.string.error_unable_to_rename_layout_file_not_found));
            return;
        }

        File layoutFileDest = new File(projectFolder, LayoutModel.getLayoutFileName(newLayoutName));
        if (layoutFileDest.exists()) {
            callback.onFail(context.getString(R.string.error_unable_to_rename_layout_file_already_exists));
            return;
        }

        if (layoutFile.renameTo(layoutFileDest)) {

            File layoutThumbnailFile = new File(projectFolder, LayoutModel.getBitmapFileName(layoutModel.name, true));
            File layoutThumbnailFileDest = new File(projectFolder, LayoutModel.getBitmapFileName(newLayoutName, true));
            layoutThumbnailFile.renameTo(layoutThumbnailFileDest);

            File layoutScreenshotFile = new File(projectFolder, LayoutModel.getBitmapFileName(layoutModel.name, false));
            File layoutScreenshotFileDest = new File(projectFolder, LayoutModel.getBitmapFileName(newLayoutName, false));
            layoutScreenshotFile.renameTo(layoutScreenshotFileDest);

            layoutModel.name = newLayoutName;
            saveProject(projectId, project);
            callback.onSuccess(null);
        } else
            callback.onFail(context.getString(R.string.error_unable_to_rename_layout));
    }


    @Override
    public void duplicateLayout(String projectId, String layoutId, OperationCallback<LayoutModel> callback) {

        File projectFolder = getLocalProjectFolderById(projectId, false);
        if (projectFolder == null) {
            callback.onFail(context.getString(R.string.error_unable_to_duplicate_layout));
            return;
        }

        Project project = ensureLocalProject(projectId);
        LayoutModel layoutModel = project.findById(layoutId);

        if (layoutModel == null) {
            callback.onFail(context.getString(R.string.error_unable_to_duplicate_layout));
            return;
        }

        File layoutFile = new File(projectFolder, LayoutModel.getLayoutFileName(layoutModel.name));
        if (!layoutFile.exists()) {
            callback.onFail(context.getString(R.string.error_unable_to_duplicate_layout));
            return;
        }

        String newLayoutName = createLayoutName(projectId, layoutModel.getName());

        if (newLayoutName == null) {
            callback.onFail(context.getString(R.string.error_unable_to_duplicate_layout));
            return;
        }

        File layoutFileDest = new File(projectFolder, LayoutModel.getLayoutFileName(newLayoutName));
        if (layoutFileDest.exists()) {
            callback.onFail(context.getString(R.string.error_unable_to_duplicate_layout));
            return;
        }

        if (FileUtils.copyFile(layoutFile, layoutFileDest)) {

            File layoutThumbnailFile = new File(projectFolder, LayoutModel.getBitmapFileName(layoutModel.name, true));
            File layoutThumbnailFileDest = new File(projectFolder, LayoutModel.getBitmapFileName(newLayoutName, true));
            FileUtils.copyFile(layoutThumbnailFile, layoutThumbnailFileDest);

            File layoutScreenshotFile = new File(projectFolder, LayoutModel.getBitmapFileName(layoutModel.name, false));
            File layoutScreenshotFileDest = new File(projectFolder, LayoutModel.getBitmapFileName(newLayoutName, false));
            FileUtils.copyFile(layoutScreenshotFile, layoutScreenshotFileDest);


            LayoutModel newLayoutModel = new LayoutModel(Utils.genUUID(), newLayoutName, new Date());
            project.layoutModels.add(newLayoutModel);

            saveProject(projectId, project);
            callback.onSuccess(newLayoutModel);
        } else
            callback.onFail(context.getString(R.string.error_unable_to_duplicate_layout));
    }

    @Override
    public String getMainLayoutId(String projectId) {
        Project project = ensureLocalProject(projectId);
        return project.mainLayoutId;
    }

    @Override
    public void setMainLayoutId(String projectId, String layoutId, OperationCallback<Void> callback) {
        Project project = ensureLocalProject(projectId);
        project.mainLayoutId = layoutId;
        saveProject(projectId, project);
        callback.onSuccess(null);
    }

    @Override
    public void deleteProject(String projectId, OperationCallback<Void> callback) {
        File projectFolder = getLocalProjectFolderById(projectId, false);

        deleteFileOrFolder(projectFolder);

        Projects projects = ensureLocalProjects();
        projects.removeById(projectId);
        saveProjects(projects);

        projectLocalMap.remove(projectId);

        callback.onSuccess(null);
    }

    @Override
    public void renameProject(String projectId, String newProjectName, OperationCallback<Void> callback) {
        File projectFolder = getLocalProjectFolderById(projectId, false);
        File projectFolderDest = getLocalProjectFolderByName(newProjectName, false);

        if (projectFolderDest == null || projectFolder == null || !projectFolder.exists()) {
            callback.onFail(context.getString(R.string.error_unable_to_rename_project));
            return;
        }

        if (projectFolderDest.exists()) {
            callback.onFail(context.getString(R.string.error_unable_to_rename_project_folder_exists));
            return;
        }

        if (projectFolder.renameTo(projectFolderDest)) {
            Projects projects = ensureLocalProjects();
            ProjectModel projectModel = projects.findById(projectId);
            if (projectModel != null) {
                projectModel.name = newProjectName;
                saveProjects(projects);
            }
            callback.onSuccess(null);
        } else
            callback.onFail(context.getString(R.string.error_unable_to_rename_project));
    }

    public Bitmap getLayoutBitmap(String projectId, String layoutId, boolean thumbnail) {
        Projects localProjects = ensureLocalProjects();
        ProjectModel projectModel = localProjects.findById(projectId);
        if (projectModel != null) {

            File projectFolder = getLocalProjectFolderById(projectId, false);
            if (projectFolder == null || !projectFolder.exists())
                return null;

            Project project = ensureLocalProject(projectId);
            LayoutModel layoutModel = project.findById(layoutId);

            if (layoutModel != null) {
                String fileName = LayoutModel.getBitmapFileName(layoutModel.name, thumbnail);
                File bitmapFile = new File(projectFolder, fileName);
                if (bitmapFile.exists())
                    return BitmapFactory.decodeFile(bitmapFile.getPath());
            }

        } else {
            Projects internalProjects = ensureInternalProjects();
            projectModel = internalProjects.findById(projectId);

            if (projectModel != null) {
                Project project = ensureInternalProject(projectId);
                LayoutModel layoutModel = project.findById(layoutId);

                if (layoutModel != null)
                    try {
                        String fileName = LayoutModel.getBitmapFileName(layoutModel.name, thumbnail);
                        return BitmapFactory.decodeStream(context.getAssets().open(INTERNAL_PROJECTS_FOLDER + "/" + projectModel.name + "/" + fileName));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
        }

        return null;
    }

    @Override
    public Bitmap getLayoutThumbnail(String projectId, String layoutId) {
        return getLayoutBitmap(projectId, layoutId, true);
    }

    @Override
    public Bitmap getLayoutThumbnail1(String projectId) {
        Project project = ensureAnyProject(projectId);
        if (project.layoutModels.size() > 0)
            return getLayoutBitmap(projectId, project.layoutModels.get(0).id, true);
        return null;
    }

    @Override
    public Bitmap getLayoutThumbnail2(String projectId) {
        Project project = ensureAnyProject(projectId);
        if (project.layoutModels.size() > 1)
            return getLayoutBitmap(projectId, project.layoutModels.get(1).id, true);
        return null;
    }

    @Override
    public Bitmap getLayoutThumbnail3(String projectId) {
        Project project = ensureAnyProject(projectId);
        if (project.layoutModels.size() > 2)
            return getLayoutBitmap(projectId, project.layoutModels.get(2).id, true);
        return null;
    }

    @Override
    public Bitmap getLayoutThumbnail4(String projectId) {
        Project project = ensureAnyProject(projectId);
        if (project.layoutModels.size() > 3)
            return getLayoutBitmap(projectId, project.layoutModels.get(3).id, true);
        return null;
    }

    @Override
    public Bitmap getLayoutScreenshot(String projectId, String layoutId) {
        return getLayoutBitmap(projectId, layoutId, false);
    }

    @Override
    public LayoutDescriptor loadLayout(String projectId, String layoutId) {
        final ResultHolder<LayoutDescriptor> holder = new ResultHolder<>();
        loadLayout(projectId, layoutId, new OperationCallback<LayoutDescriptor>() {
            @Override
            public void onSuccess(LayoutDescriptor result) {
                holder.result = result;
            }

            @Override
            public void onFail(String message) {
            }
        });
        return holder.result;
    }

    @Override
    public LayoutDescriptor loadLayoutByName(String projectName, String layoutName) {
        ProjectModel projectModel;
        LayoutModel layoutModel;

        Projects internalProjects = ensureInternalProjects();
        Projects localProjects = ensureLocalProjects();

        projectModel = internalProjects.findByName(projectName);
        if (projectModel == null)
            projectModel = localProjects.findByName(projectName);

        if (projectModel == null)
            return null;

        Project project = ensureAnyProject(projectModel.id);

        layoutModel = project.findByName(layoutName);
        if (layoutModel == null)
            return null;

        return loadLayout(projectModel.id, layoutModel.id);
    }

    @Override
    public void loadLayout(String projectId, String layoutId, final OperationCallback<LayoutDescriptor> callback) {
        loadLayoutAsString(projectId, layoutId, new OperationCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LayoutDescriptor layoutDescriptor = fromString(result, LayoutDescriptor.class);
                if (layoutDescriptor == null)
                    callback.onFail(context.getString(R.string.error_unable_to_load_layout));
                else
                    callback.onSuccess(layoutDescriptor);
            }

            @Override
            public void onFail(String message) {
                callback.onFail(message);
            }
        });
    }

    private void loadLayoutFromInputStreamAsString(InputStream inputStream, OperationCallback<String> callback) {
        try (Reader reader = new InputStreamReader(inputStream)) {

            StringBuilder sb = new StringBuilder();

            char[] buf = new char[1024];
            int l;
            while ((l = reader.read(buf)) != -1)
                sb.append(buf, 0, l);

            callback.onSuccess(sb.toString());

        } catch (IOException e) {
            e.printStackTrace();
            callback.onFail(context.getString(R.string.error_unable_to_load_layout));
        }
    }

    private void loadLocalLayoutAsString(ProjectModel projectModel, LayoutModel layoutModel, OperationCallback<String> callback) {

        File projectFolder = getLocalProjectFolderByName(projectModel.name, false);
        if (projectFolder == null || !projectFolder.exists()) {
            callback.onFail(context.getString(R.string.error_unable_to_load_layout));
            return;
        }

        File layoutFile = new File(projectFolder, LayoutModel.getLayoutFileName(layoutModel.name));

        try {
            loadLayoutFromInputStreamAsString(new FileInputStream(layoutFile), callback);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            callback.onFail(context.getString(R.string.error_unable_to_load_layout));
        }
    }

    private void loadInternalLayoutAsString(ProjectModel projectModel, LayoutModel layoutModel, OperationCallback<String> callback) {
        AssetManager assetManager = context.getAssets();
        try {
            loadLayoutFromInputStreamAsString(assetManager.open(INTERNAL_PROJECTS_FOLDER + "/" + projectModel.name + "/" + LayoutModel.getLayoutFileName(layoutModel.name)), callback);
        } catch (IOException e) {
            e.printStackTrace();
            callback.onFail(context.getString(R.string.error_unable_to_load_layout));
        }
    }

    @Override
    public void loadLayoutAsString(String projectId, String layoutId, OperationCallback<String> callback) {

        if (projectId == null || layoutId == null) {
            callback.onFail(context.getString(R.string.error_unable_to_load_layout));
            return;
        }

        Projects localProjects = ensureLocalProjects();
        ProjectModel projectModel = localProjects.findById(projectId);
        if (projectModel != null) {

            Project localProject = ensureLocalProject(projectId);
            LayoutModel layoutModel = localProject.findById(layoutId);
            if (layoutModel != null)
                loadLocalLayoutAsString(projectModel, layoutModel, callback);
            else
                callback.onFail(context.getString(R.string.error_unable_to_load_layout));

        } else {
            Projects internalProjects = ensureInternalProjects();
            projectModel = internalProjects.findById(projectId);
            if (projectModel != null) {
                Project internalProject = ensureInternalProject(projectId);
                LayoutModel layoutModel = internalProject.findById(layoutId);
                if (layoutModel != null)
                    loadInternalLayoutAsString(projectModel, layoutModel, callback);
                else
                    callback.onFail(context.getString(R.string.error_unable_to_load_layout));
            } else
                callback.onFail(context.getString(R.string.error_unable_to_load_layout));
        }
    }

    private void makeScreenShot(final String projectId, final String layoutName, LayoutDescriptor layoutDescriptor) {

        final File projectFolder = getLocalProjectFolderById(projectId, false);
        if (projectFolder == null || !projectFolder.exists())
            return;

        LayoutScreenshoter.screenshot(context, layoutDescriptor, new ImageProvider() {
            @Override
            public Bitmap loadImage(String imageFileName, int reqWidth, int reqHeight) {
                return ProjectServiceImpl.this.loadImage(projectId, imageFileName, reqWidth, reqHeight);
            }
        }, new OperationCallback<Bitmap>() {
            @Override
            public void onSuccess(Bitmap bitmap) {
                try {
//                    File bitmapFile = new File(projectFolder, layoutName + ".png");
//                    bitmap.compress(Bitmap.CompressFormat.PNG, 10, new FileOutputStream(bitmapFile));

                    final int mini = 360;

                    float scale;
                    if (bitmap.getWidth() > bitmap.getHeight())
                        scale = (float) mini / bitmap.getWidth();
                    else
                        scale = (float) mini / bitmap.getHeight();

                    Matrix matrix = new Matrix();
                    matrix.postScale(scale, scale);

                    File bitmapFileMini = new File(projectFolder, LayoutModel.getBitmapFileName(layoutName, true));
                    Bitmap bitmapMini = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                    bitmapMini.compress(Bitmap.CompressFormat.PNG, 10, new FileOutputStream(bitmapFileMini));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(String message) {
            }
        });
    }

    @Override
    public void saveLayout(final String projectId, final String layoutId, final LayoutDescriptor layoutDescriptor, final OperationCallback<LayoutModel> callback) {
        if (projectId == null || layoutDescriptor == null) {
            callback.onFail(context.getString(R.string.error_unable_to_save_layout));
            return;
        }

        final File projectFolder = getLocalProjectFolderById(projectId, false);
        if (projectFolder == null || !projectFolder.exists()) {
            callback.onFail(context.getString(R.string.error_unable_to_save_layout));
            return;
        }

        Project project = ensureLocalProject(projectId);
        LayoutModel layoutModel = project.findById(layoutId);
        if (layoutModel == null) {
            layoutModel = new LayoutModel(Utils.genUUID(), createLayoutName(projectId, "Layout"), new Date());
            project.layoutModels.add(layoutModel);
        } else
            layoutModel.lastModified = new Date();
        saveProject(projectId, project);

        boolean result = JsonUtils.toFile(layoutDescriptor, new File(projectFolder, LayoutModel.getLayoutFileName(layoutModel.getName())), false);

        if (result) {
            makeScreenShot(projectId, layoutModel.getName(), layoutDescriptor);

            Projects projects = ensureLocalProjects();
            ProjectModel projectModel = projects.findById(projectId);
            if (projectModel != null) {
                projectModel.layoutCount = project.layoutModels.size();
                saveProjects(projects);
            }
        }

        callback.onSuccess(layoutModel);
    }

    @Override
    public Bitmap loadImage(String projectId, String imageFileName, int reqWidth, int reqHeight) {
        if (imageFileName == null)
            return null;

        final int slashIdx = imageFileName.indexOf('/');
        if (slashIdx != -1) {
            projectId = imageFileName.substring(0, slashIdx);
            imageFileName = imageFileName.substring(slashIdx + 1);
        }

        Projects localProjects = ensureLocalProjects();
        ProjectModel projectModel = localProjects.findById(projectId);
        if (projectModel != null) {

            File projectFolder = getLocalProjectFolderById(projectId, false);
            if (projectFolder == null || !projectFolder.exists())
                return null;

            String fileName = IMAGES_FOLDER + "/" + imageFileName;
            File bitmapFile = new File(projectFolder, fileName);
            return BitmapUtils.decodeSampledBitmapFromFile(bitmapFile.getPath(), reqWidth, reqHeight);

        } else {
            Projects internalProjects = ensureInternalProjects();
            projectModel = internalProjects.findById(projectId);
            if (projectModel != null)
                return BitmapUtils.decodeSampledBitmapFromAssets(context,
                        INTERNAL_PROJECTS_FOLDER + "/" + projectModel.name + "/" + IMAGES_FOLDER + "/" + imageFileName,
                        reqWidth, reqHeight);
        }

        return null;
    }

    @Override
    public String saveImage(String projectId, String pathToImageFile) {
        if (pathToImageFile == null)
            return null;

        File projectFolder = getLocalProjectFolderById(projectId, false);
        if (projectFolder == null)
            return null;

        File sourceFile = new File(pathToImageFile);
        if (!sourceFile.exists())
            return null;

        String imageFileName = sourceFile.getName();

        File targetFolder = new File(projectFolder, IMAGES_FOLDER);
        if (!targetFolder.exists())
            if (!targetFolder.mkdirs())
                return null;

        File targetFile = new File(targetFolder, imageFileName);

        if (StringUtils.equals(sourceFile.getPath(), targetFile.getPath()))
            return imageFileName;

        if (FileUtils.copyFile(sourceFile, targetFile))
            return imageFileName;

        return null;
    }

    @Override
    public void makeZip(String projectId, OperationCallback<String> callback) {

        Projects localProjects = ensureLocalProjects();
        ProjectModel projectModel = localProjects.findById(projectId);
        if (projectModel == null) {
            callback.onFail(context.getString(R.string.error_unable_to_zip_project));
            return;
        }

        File projectFolder = getLocalProjectFolderByName(projectModel.name);
        if (projectFolder == null) {
            callback.onFail(context.getString(R.string.error_unable_to_zip_project));
            return;
        }

        File tmpFolder = getLocalTmpFolder();
        if (tmpFolder == null) {
            callback.onFail(context.getString(R.string.error_unable_to_zip_project));
            return;
        }

        File tmpFile = new File(tmpFolder, projectModel.name + ".zip");

        try {
            try (Zip zip = new Zip(tmpFile)) {
                zip.appendFolder(projectFolder);
            }
            callback.onSuccess(tmpFile.getPath());
        } catch (IOException e) {
            callback.onFail(context.getString(R.string.error_unable_to_zip_project));
            e.printStackTrace();
        }
    }

    @Override
    public void importZip(String pathToZipFile, OperationCallback<ProjectModel> callback) {
        String projectName = FileUtils.extractFileBaseName(FileUtils.extractFileName(pathToZipFile));

        if (TextUtils.isEmpty(projectName)) {
            callback.onFail(context.getString(R.string.error_unable_to_import_zip));
            return;
        }

        File localStorageFolder = getLocalStorageFolder();
        if (localStorageFolder == null) {
            callback.onFail(context.getString(R.string.error_unable_to_import_zip));
            return;
        }

        File importedProjectFolder = new File(localStorageFolder, projectName);
        if (importedProjectFolder.exists()) {
            projectName = createProjectName(projectName);
            if (projectName == null) {
                callback.onFail(context.getString(R.string.error_unable_to_import_zip));
                return;
            }
            importedProjectFolder = new File(localStorageFolder, projectName);
        }

        if (importedProjectFolder.exists()) {
            callback.onFail(context.getString(R.string.error_unable_to_import_zip));
            return;
        }

        if (!importedProjectFolder.mkdirs()) {
            callback.onFail(context.getString(R.string.error_unable_to_import_zip));
            return;
        }

        try {
            UnZip unZip = new UnZip(new File(pathToZipFile));
            unZip.extract(importedProjectFolder);
        } catch (IOException e) {
            deleteFileOrFolder(importedProjectFolder);
            e.printStackTrace();
            callback.onFail(context.getString(R.string.error_unable_to_import_zip));
            return;
        }

        Project importedProject = JsonUtils.fromFile(new File(importedProjectFolder, PROJECT_FILE_NAME), Project.class);
        if (importedProject == null) {
            deleteFileOrFolder(importedProjectFolder);
            callback.onFail(context.getString(R.string.error_unable_to_import_zip));
            return;
        }

        Projects projects = ensureLocalProjects();
        ProjectModel projectModel = projects.findById(importedProject.id);
        if (projectModel != null) {
            deleteFileOrFolder(importedProjectFolder);
            callback.onFail(context.getString(R.string.error_unable_to_import_zip_project_id_already_exists, projectModel.name));
            return;
        }

        int layoutCount = importedProject.layoutModels == null ? 0 : importedProject.layoutModels.size();
        projectModel = new ProjectModel(ProjectModel.ProjectType.LOCAL_FILE_SYSTEM, importedProject.id, projectName, layoutCount, new Date());
        projects.projectModels.add(projectModel);
        saveProjects(projects);

        callback.onSuccess(projectModel);
    }

    @Override
    public void previewLayout(String projectId, String layoutId) {
        Intent intent = new Intent(context, PreviewActivity.class);
        intent.putExtra(Arguments.ARGUMENT_LAYOUT_ID, layoutId);
        intent.putExtra(Arguments.ARGUMENT_PROJECT_ID, projectId);
        context.startActivity(intent);
    }

    @Override
    public void previewMainLayout(String projectId) {
        Project project = ensureAnyProject(projectId);
        if (project.mainLayoutId != null)
            previewLayout(projectId, project.mainLayoutId);
        else if (project.layoutModels.size() > 0)
            previewLayout(projectId, project.layoutModels.get(0).id);
    }

    @Override
    public void generatePdf(String projectId, String layoutId, OperationCallback<String> callback) {
        File tmpFolder = getLocalTmpFolder();
        if (tmpFolder == null) {
            callback.onFail(context.getString(R.string.error_unable_to_generate_pdf));
            return;
        }

        String projectName = formatProjectName(projectId);
        String layoutName = formatLayoutName(projectId, layoutId);

        String pdfFileName = projectName + "_" + layoutName + ".pdf";

        File pdfFile = new File(tmpFolder, pdfFileName);

        PdfLayoutGenerator generator = new PdfLayoutGenerator(context, projectId, layoutId);
        try {
            try (OutputStream outputStream = new FileOutputStream(pdfFile)) {
                generator.generate(outputStream);
            }
            callback.onSuccess(pdfFile.getPath());
        } catch (IOException e) {
            e.printStackTrace();
            callback.onFail(context.getString(R.string.error_unable_to_generate_pdf));
        }
    }
}
