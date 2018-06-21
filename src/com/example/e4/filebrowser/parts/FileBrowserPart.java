
package com.example.e4.filebrowser.parts;

import java.io.File;
import java.net.URL;

import javax.annotation.PostConstruct;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import com.example.e4.filebrowser.treeviewer.ViewContentProvider;
import com.example.e4.filebrowser.treeviewer.ViewLabelProvider;

public class FileBrowserPart {
	private TreeViewer viewer;

	@PostConstruct
	public void postConstruct(Composite parent) {
		parent.setLayout(new FillLayout(SWT.HORIZONTAL));

		// Folder.png
		ImageDescriptor folderImage = createImage("icons/folder.png");
		ImageDescriptor fileImage = createImage("icons/page.png");

		viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		viewer.setContentProvider(new ViewContentProvider());
		viewer.setLabelProvider(new DelegatingStyledCellLabelProvider(new ViewLabelProvider(folderImage, fileImage)));
		viewer.setInput(File.listRoots());
	}

	private ImageDescriptor createImage(final String path) {
		Bundle bundle = FrameworkUtil.getBundle(ViewLabelProvider.class);
		URL url = FileLocator.find(bundle, new Path(path), null);
		return ImageDescriptor.createFromURL(url);
	}

	@Focus
	public void setFocus() {
		viewer.getControl().setFocus();
	}
}
