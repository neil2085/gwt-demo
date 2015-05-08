package com.demo.items.client;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

public class ItemsShell extends Composite implements EntryPoint {

	private static ItemsShellUiBinder uiBinder = GWT
			.create(ItemsShellUiBinder.class);

	interface ItemsShellUiBinder extends UiBinder<Widget, ItemsShell> {
	}


	public ItemsShell() {
		initWidget(uiBinder.createAndBindUi(this));
		menuDataProvider.addDataDisplay(menuCellList);
		
		addEample(new ExampleHolder("Cell List", new ItemListView()));	
		addEample(new ExampleHolder("Cell Table", new CellTableView()));
		addEample(new ExampleHolder("Cell Tree", new CellTreeView()));
		addEample(new ExampleHolder("Tab View", new TabPanelView()));
		
		menuCellList.setSelectionModel(menuSelectionModel);
		menuSelectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			
			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
				ExampleHolder aHolder = menuSelectionModel.getSelectedObject();
				contentPanel.setWidget(aHolder.getExample());				
			}
		});
		
	}

	@UiField
	SimpleLayoutPanel contentPanel;
	
	@UiField(provided=true)
	CellList<ExampleHolder> menuCellList = new CellList<ExampleHolder>(new ExampleMenuCell());

	final ListDataProvider<ExampleHolder> menuDataProvider = new ListDataProvider<>();
	final SingleSelectionModel<ExampleHolder> menuSelectionModel = new SingleSelectionModel<>();
	
	
	@Override
	public void onModuleLoad() {

		RootLayoutPanel rp = RootLayoutPanel.get();
		rp.add(this);
		rp.ensureDebugId("outerid");
	
	}

	void addEample(ExampleHolder eHolder){
		menuDataProvider.getList().add(eHolder);
	}
	
	class ExampleHolder {
		public ExampleHolder(String title,Composite exampleComp){
			this.menuTitle = title;
			this.example = exampleComp;
		}
		
		private String menuTitle;
		private Composite example;
		
		public Composite getExample() {
			return example;
		}
		public String getMenuTitle() {
			return menuTitle;
		}
	}
	
	class ExampleMenuCell extends AbstractCell<ExampleHolder>{
		@Override
		public void render(com.google.gwt.cell.client.Cell.Context context,
				ExampleHolder value, SafeHtmlBuilder sb) {
			sb.appendEscaped(value.getMenuTitle());
			
		}
	}
}
