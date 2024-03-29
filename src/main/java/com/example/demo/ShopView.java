package com.example.demo;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.StringToDoubleConverter;
import com.vaadin.flow.function.SerializableFunction;
import com.vaadin.flow.router.Route;

@Route("shop")
public class ShopView extends VerticalLayout{

  private ShopRepository repository;
  private TextField productName = new TextField("Product Name");
  private TextField productDescription = new TextField("Product Description");
  private NumberField productPrice = new NumberField("Product Price");
  private NumberField productQuantity = new NumberField("Product Quantity");
  private Grid<ShopInventory> grid = new Grid<>(ShopInventory.class);
  private Binder<ShopInventory> binder = new Binder<>(ShopInventory.class);
  
  public ShopView( ShopRepository repository )
  {
    this.repository = repository;

    grid.setColumns("productName", "productDescription", "productPrice", "productQuantity");
    add(getForm(), grid);
    refreshGrid();
  }

  private Component getForm()
  {
    var layout = new HorizontalLayout();
    layout.setAlignItems(Alignment.BASELINE);

    var addButton = new Button("Add");

    addButton.addClickShortcut(Key.ENTER);
    addButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    layout.add(productName, productDescription, productPrice, productQuantity, addButton);
    binder.bindInstanceFields(this);


    addButton.addClickListener(click -> {
      try{
        var shopInventory = new ShopInventory();
        binder.writeBean(shopInventory);
        repository.save(shopInventory);
        binder.readBean(new ShopInventory());
        refreshGrid();
      }
      catch(ValidationException e)
      {
      }
    });

    return layout;
  }

  private void refreshGrid()
  {
    grid.setItems(repository.findAll());
  }
  
}

