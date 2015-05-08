package com.demo.items.client;

import java.util.Comparator;
import java.util.List;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SingleSelectionModel;

public class CellTableView extends Composite {

	private static CellTableViewUiBinder uiBinder = GWT
			.create(CellTableViewUiBinder.class);

	interface CellTableViewUiBinder extends UiBinder<Widget, CellTableView> {
	}

	@UiField(provided = true)
	CellTable<ContactInfo> cellTabView  ;
	
	@UiField(provided=true)
	DataGrid<ContactInfo> dataGridView ;
	
	@UiField
	Button addButton;
	
	ListDataProvider<ContactInfo> dataProvider = new ListDataProvider<>();

		

	public CellTableView() {
		
		initCellTable();
		
		initDataGrid();
		
		initWidget(uiBinder.createAndBindUi(this));
				
	    addButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				dataProvider.getList().add(new ContactInfo("John", "1"));
				
			}
		});
	}

	

	private void initDataGrid(){
		
		dataGridView = new DataGrid<>(KEY_PROVIDER);
		
		
		
		dataProvider.addDataDisplay(dataGridView);
				
		populateSampleData(dataProvider.getList());
		
		dataGridView.setWidth("250px");
		
		dataGridView.setHeight("300px");
		
		Column<ContactInfo, String> nameColumn = new Column<ContactInfo, String>(
				new TextCell()) {
			@Override
			public String getValue(ContactInfo object) {
				return object.getName();
			}
		};
		dataGridView.addColumn(nameColumn,"Name");
		dataGridView.setColumnWidth(nameColumn, 60, Unit.PX);

		dataGridView.setSelectionModel(new SingleSelectionModel<ContactInfo>());
	}
	
	
    public static final ProvidesKey<ContactInfo> KEY_PROVIDER = new ProvidesKey<ContactInfo>() {
        @Override
        public Object getKey(ContactInfo item) {
          return item == null ? null : item.getName();
        }
    };
    
    
	private void populateSampleData(List<ContactInfo> list){
		list.add(new ContactInfo("Tom", "Cheng"));
		list.add(new ContactInfo("Mike", "Wang"));
		list.add(new ContactInfo("John", "Li"));
	}
	
	private void initCellTable() {
		
		cellTabView = new CellTable<>();
		
		final ListDataProvider<ContactInfo> cellTabDataProvider = new ListDataProvider<>();
		
		cellTabDataProvider.addDataDisplay(cellTabView);

		populateSampleData(cellTabDataProvider.getList());

		Column<ContactInfo, String> nameColumn = new Column<ContactInfo, String>(
				new TextCell()) {
			@Override
			public String getValue(ContactInfo object) {
				return object.getName();
			}
		};
		cellTabView.addColumn(nameColumn,"Name");
		nameColumn.setSortable(true);

		
		Column<ContactInfo, String> addressColumn = new Column<CellTableView.ContactInfo, String>(new TextCell()) {
			
			@Override
			public String getValue(ContactInfo object) {
				return object.getAddress();
			}
		};
		cellTabView.addColumn(addressColumn,"Address");
		
		
		//add sorting event
	    ListHandler<ContactInfo> sortHandler = new ListHandler<ContactInfo>(
	    		cellTabDataProvider.getList());
	    cellTabView.addColumnSortHandler(sortHandler);
	    
	    sortHandler.setComparator(nameColumn, new Comparator<CellTableView.ContactInfo>() {
			
			@Override
			public int compare(com.demo.items.client.CellTableView.ContactInfo o1,
					com.demo.items.client.CellTableView.ContactInfo o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});
	    

	}

	class ContactInfo {
		private String name;
		private String address;

		public ContactInfo(String name, String address) {
			this.name = name;
			this.address = address;
		}

		public String getName() {
			return name;
		}

		public String getAddress() {
			return address;
		}
	}
}
