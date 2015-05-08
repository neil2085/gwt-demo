package com.demo.items.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.TreeViewModel;

public class CellTreeView extends Composite {

	private static CellTreeViewUiBinder uiBinder = GWT
			.create(CellTreeViewUiBinder.class);

	interface CellTreeViewUiBinder extends UiBinder<Widget, CellTreeView> {
	}

	@UiField(provided = true)
	CellTree cellTreeView;

	public CellTreeView() {
		
		//CellTree.Resources res = GWT.create(CellTree.BasicResources.class);
		//cellTreeView = new CellTree(new CustomTreeModel(),"root",res);
		
		cellTreeView = new CellTree(new CustomTreeModel(),"root but not generated");

		initWidget(uiBinder.createAndBindUi(this));
	}

	/**
	 * The model that defines the nodes in the tree.
	 */
	private static class CustomTreeModel implements TreeViewModel {

		/**
		 * Get the {@link NodeInfo} that provides the children of the specified
		 * value.
		 */
		public <T> NodeInfo<?> getNodeInfo(T value) {
			
			if(value instanceof String){
				ListDataProvider<Category> dataProvider = new ListDataProvider<Category>();
				Category cat1 = new Category("cat111");
				cat1.addMusic(new Music("m-1.1"));
				cat1.addMusic(new Music("m-1.2"));
				
				Category cat2 = new Category("cat222");
				cat2.addMusic(new Music("m-2.1"));
				cat2.addMusic(new Music("m-2.2"));
				
				
				dataProvider.getList().add(cat1);
				dataProvider.getList().add(cat2);
				
				return new DefaultNodeInfo<Category>(dataProvider, new AbstractCell<Category>() {

					@Override
					public void render(
							com.google.gwt.cell.client.Cell.Context context,
							Category value, SafeHtmlBuilder sb) {
						sb.appendHtmlConstant("<b>" + value.getName() + "</b>");
						
					}
				});
				
			}else if(value instanceof Category){
				ListDataProvider<Music> dataProvider = new ListDataProvider<Music>();
				dataProvider.getList().addAll(((Category) value).getMusics());
				
				return new DefaultNodeInfo<Music>(dataProvider, new AbstractCell<Music>() {

					@Override
					public void render(
							com.google.gwt.cell.client.Cell.Context context,
							Music value, SafeHtmlBuilder sb) {
						sb.appendEscaped( value.getTitle());
						
					}
				});
			}
			return null;
			

		}

		/**
		 * Check if the specified value represents a leaf node. Leaf nodes
		 * cannot be opened.
		 */
		public boolean isLeaf(Object value) {

			return value==null;
		}
	}

	static class Music {
		private String title;

		public Music(String title) {
			this.title = title;
		}

		public String getTitle() {
			return title;
		}
	}
	
	static class Category{
		private String name;
		
		public Category(String name) {
			this.name = name;
		}
		
		private List<Music> musics = new ArrayList<>();
		
		public List<Music> getMusics() {
			return musics;
		}
		public String getName() {
			return name;
		}
		public void addMusic(Music music){
			this.musics.add(music);
		}
	}
}
