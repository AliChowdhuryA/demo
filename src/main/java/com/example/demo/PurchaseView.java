package com.example.demo;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.router.Route;

@Route("purchase")
public class PurchaseView extends VerticalLayout {

    private ShopRepository repository;
    private Grid<ShopInventory> grid = new Grid<>(ShopInventory.class);
    private NumberField purchaseQuantity = new NumberField("Purchase Quantity");
    private Button purchaseButton = new Button("Purchase");

    public PurchaseView(ShopRepository repository) {
        this.repository = repository;

        grid.setColumns("productName", "productDescription", "productPrice", "productQuantity");
        grid.asSingleSelect().addValueChangeListener(event -> purchaseButton.setEnabled(event.getValue() != null));

        purchaseButton.addClickListener(event -> {
            ShopInventory selectedProduct = grid.asSingleSelect().getValue();
            if (selectedProduct != null) {
                int quantityToPurchase = purchaseQuantity.getValue().intValue();
                if (selectedProduct.getProductQuantity() >= quantityToPurchase) {
                    selectedProduct.setProductQuantity(selectedProduct.getProductQuantity() - quantityToPurchase);
                    repository.save(selectedProduct);
                    refreshGrid();
                } else {
                    Notification.show("Not enough quantity in stock");
                }
            }
        });

        add(grid, purchaseQuantity, purchaseButton);
        refreshGrid();
    }

    private void refreshGrid() {
        grid.setItems(repository.findAll());
    }
}