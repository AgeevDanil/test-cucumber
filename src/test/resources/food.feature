Feature: Food Management

  @all
  Scenario: Добавление нового овоща
    Given I navigate to the food page
    When I click the "Add" button
    And I enter the name "Кабачок"
    And I select type "VEGETABLE"
    And I save the item
    Then the item "Кабачок" with type "Овощ" and exotic "false" should be present in the list
    And the database should contain the item "Кабачок" with type "VEGETABLE" and exotic "false"

  @all
  Scenario: Add an exotic fruit
    Given I navigate to the food page
    When I click the "Add" button
    And I enter the name "Папая"
    And I select type "FRUIT"
    And I mark the item as exotic
    And I save the item
    Then the item "Папая" with type "Фрукт" and exotic "true" should be present in the list
    And the database should contain the item "Папая" with type "FRUIT" and exotic "true"

  @all
  Scenario: Prevent duplicate entries
    Given I navigate to the food page
    When I click the "Add" button
    And I enter the name "Капуста"
    And I select type "VEGETABLE"
    And I save the item
    Then the item "Капуста" should not have duplicates in the list
    And the database should not contain a single entry for "Капуста" with type "VEGETABLE"

  @all
  Scenario: Clean up after test
    Given I clean up unwanted food items
