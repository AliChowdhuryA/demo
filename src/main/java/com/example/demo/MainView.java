package com.example.demo;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.internal.ComponentTracker;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.Route;

@Route("person")
public class MainView extends VerticalLayout{
  
  private PersonRepository repository;
  private TextField firstName = new TextField("First name");
  private TextField lastName = new TextField("Last name");
  private EmailField email = new EmailField("Email");
  private Grid<Person> grid = new Grid<>(Person.class);
  private Binder<Person> binder = new Binder<>(Person.class);

  public MainView( PersonRepository repository ){
    this.repository = repository;

    grid.setColumns("firstName", "lastName", "email");
    add(getForm(), grid);
    refreshGrid();
    }

    private Component getForm(){
      var layout = new HorizontalLayout();
      layout.setAlignItems(Alignment.BASELINE);

      var addButton = new Button("Add");
      addButton.addClickShortcut(Key.ENTER);
      addButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
      layout.add(firstName, lastName, email, addButton);
      
      binder.bindInstanceFields(this);

      addButton.addClickListener(click -> {
        try{
          var person = new Person();
          binder.writeBean(person);
          repository.save(person);
          binder.readBean(new Person());
          refreshGrid();
        }
        catch(ValidationException e)
        {

        }
      });
      return layout;
    }

    private void refreshGrid(){
      grid.setItems(repository.findAll());
    }
  
}
