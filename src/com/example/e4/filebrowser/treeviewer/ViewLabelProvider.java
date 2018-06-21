package com.example.e4.filebrowser.treeviewer;

import java.io.File;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.swt.graphics.Image;

public class ViewLabelProvider extends LabelProvider implements IStyledLabelProvider {
	private ImageDescriptor directoryImage;
	private ImageDescriptor fileImage;
	private ResourceManager resourceManager;

	public ViewLabelProvider(ImageDescriptor directoryImage, ImageDescriptor fileImage) {
		this.directoryImage = directoryImage;
		this.fileImage = fileImage;
	}

	@Override
	public StyledString getStyledText(Object element) {
		if (element instanceof File) {
			File file = (File) element;
			StyledString styledString = new StyledString(getFileName(file));
			String[] files = file.list();
			if (files != null) {
				styledString.append(" ( " + files.length + " ) ", StyledString.COUNTER_STYLER);
			}
			return styledString;
		}
		return null;
	}

	@Override
	public Image getImage(Object element) {
		if (element instanceof File) {
			if (((File) element).isDirectory()) {
				return getResourceManager().createImage(directoryImage);
			}
			if (((File) element).isFile()) {
				return getResourceManager().createImage(fileImage);
			}
		}
		return super.getImage(element);
	}

	@Override
	public void dispose() {
		// garbage collect system resources
		if (resourceManager != null) {
			resourceManager.dispose();
			resourceManager = null;
		}
	}

	protected ResourceManager getResourceManager() {
		if (resourceManager == null) {
			resourceManager = new LocalResourceManager(JFaceResources.getResources());
		}
		return resourceManager;
	}

	private String getFileName(File file) {
		String name = file.getName();
		return name.isEmpty() ? file.getPath() : name;
	}
}