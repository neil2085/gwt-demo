package com.demo.items.client;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.RowCountChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

public class ItemListView extends Composite  {

	private static ItemListViewUiBinder uiBinder = GWT
			.create(ItemListViewUiBinder.class);

	@UiField
	TextBox taskName;
	@UiField
	Button addButton;
	@UiField
	Button removeButton;
	
	@UiField
	ListBox combBoxView;

	@UiField(provided = true)
	CellList<CellItem> cellListView = new CellList<CellItem>(new TaskCell());

	

	interface ItemListViewUiBinder extends UiBinder<Widget, ItemListView> {
	}

	public ItemListView() {
		
		
		
		
		initWidget(uiBinder.createAndBindUi(this));
		
		init();
		
		combBoxView.setVisibleItemCount(3);
		combBoxView.setMultipleSelect(true);
		combBoxView.addItem("Apple", "1");
		combBoxView.addItem("Orange", "2");
		combBoxView.addItem("Watermelon", "3");
		combBoxView.addItem("Vegetable", "4");
	}

	private void init() {
		// Create a list data provider.

		final ListDataProvider<CellItem> dataProvider = new ListDataProvider<CellItem>();
		
		// Add the cellList to the dataProvider.
		dataProvider.addDataDisplay(cellListView);
		
		removeButton.setVisible(false);

		addButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				// Window.alert(taskName.getText());

				dataProvider.getList().add(new CellItem(taskName.getText()));
				taskName.setText("");
				
			}
		});

		taskName.addKeyUpHandler(new KeyUpHandler() {
			
			@Override
			public void onKeyUp(KeyUpEvent event) {
				if(event.getNativeKeyCode()==13){
					
					dataProvider.getList().add(new CellItem(taskName.getText()));
					taskName.setText("");
					
				}
				
			}
		});
		
		// Add a selection model to handle user selection.
		final SingleSelectionModel<CellItem> selectionModel = new SingleSelectionModel<CellItem>();
		cellListView.setSelectionModel(selectionModel);
		/**
		 * <code>
		selectionModel
				.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
					public void onSelectionChange(SelectionChangeEvent event) {
						CellItem selected = selectionModel.getSelectedObject();
						if (selected != null) {
							Logger.getLogger("mylogger").info(
									"selected:" + selected.getTitle());
						}
					}
				});
		</code>
		 **/
		removeButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				dataProvider.getList().remove(
						selectionModel.getSelectedObject());
			}
		});

		cellListView.addRowCountChangeHandler(new RowCountChangeEvent.Handler() {
			
			@Override
			public void onRowCountChange(RowCountChangeEvent event) {
				if(event.getNewRowCount()>0){
					removeButton.setVisible(true);
				}else{
					removeButton.setVisible(false);
				}
				
			}
		});
	}

	@Override
	protected void initWidget(Widget widget) {
		// TODO Auto-generated method stub
		super.initWidget(widget);

	}


	class CellItem {
		private String title;

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public CellItem(String title) {
			this.title = title;
		}
	}

	class TaskCell extends AbstractCell<CellItem> {

		@Override
		public void render(com.google.gwt.cell.client.Cell.Context context,
				CellItem value, SafeHtmlBuilder sb) {

			sb.appendEscaped(value.getTitle());

		}
	}

}
