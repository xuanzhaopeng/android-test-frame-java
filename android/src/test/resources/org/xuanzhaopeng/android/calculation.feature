Feature: Calculation

  @regression @C000001
  Scenario Outline: I see default view when I tap calculation button with empty arguments
    Given I see main activity page
    When I tap on <op> button
    Then I see main activity page

    Examples:
      | op  |
      | add |
      | sub |
      | div |
      | mul |

  @regression @C000002
  Scenario Outline: I see default view when I tap calculation button with only 1 filled argument
    Given I see main activity page
    When I type 10 to <arg_index> argument
    And I tap on <op> button
    Then I see main activity page

    Examples:
      | op  | arg_index |
      | add | first     |
      | add | second    |
      | sub | first     |
      | sub | second    |
      | div | first     |
      | div | second    |
      | mul | first     |
      | mul | second    |

  @regression @C000003
  Scenario Outline: Calculator could accept valid number and refuse/correct invalid number
    Given I see main activity page
    When I type <value> to first argument
    And I type <value> to second argument
    Then I see the text of first argument is <expect_number>
    And I see the text of second argument is <expect_number>

    Examples:
      | value                                                                            | expect_number                                                                    |
      | 1                                                                                | 1                                                                                |
      | -1                                                                               | -1                                                                               |
      | 1000000000000000000000000000000000000000000000000000000000000000000000000000000  | 1000000000000000000000000000000000000000000000000000000000000000000000000000000  |
      | -1000000000000000000000000000000000000000000000000000000000000000000000000000000 | -1000000000000000000000000000000000000000000000000000000000000000000000000000000 |
      | 0.1                                                                              | 0.1                                                                              |
      | 0                                                                                | 0                                                                                |
      | 01                                                                               | 1                                                                                |
      | 00001                                                                            | 1                                                                                |
      | .5                                                                               | 0.5                                                                              |

  @regression @C000004
  Scenario Outline: I could do add/sub/mul/div calculations
    Given I see main activity page
    When I calculate <op>
    Then I see the text of result is <result>

    Examples:
      | op                                                                                  | result       |
      | 1 + 2                                                                               | 3            |
      | 1000000000000000000000000000000000000000000000000000000000000000000000000000000 + 1 | 1e78         |
      | -10 + -20                                                                           | -30          |
      | 0.5 + 0.6                                                                           | 1.1          |
      | -5 + 5                                                                              | 0            |
      | 40 - 10                                                                             | 30           |
      | 10 - 40                                                                             | -30          |
      | 1 - 1000000000000000000000000000000000000000000000000000000000000000000000000000000 | -1e78        |
      | -10 - -20                                                                           | 10           |
      | 0.3 - 0.4                                                                           | -0.1         |
      | 100 * 3                                                                             | 300          |
      | 1000000000000000000000000000000000000000000000000000000000000000000000000000000 * 3 | 3e78         |
      | -10 * -10                                                                           | 100          |
      | -10 * 10                                                                            | -100         |
      | 10 * 0.523                                                                          | 5.23         |
      | 0.1 * 0.523                                                                         | 0.0523       |
      | 10 / 5                                                                              | 2            |
      | 5 / 10                                                                              | 0.5          |
      | 3 / 10                                                                              | 0.3          |
      | 0 / 10                                                                              | 0            |
      | 10 / 0                                                                              | Not a number |
      | 0.1 / 0.001                                                                         | 100          |
      | 1 / 1000000000000000000000000000000000000000000000000000000000000000000000000000000 | 1e-78        |
      | 1000000000000000000000000000000000000000000000000000000000000000000000000000000 / 1 | 1e78         |
      | -10 / -10                                                                           | 1            |
      | -10 / 10                                                                            | -1           |

  @regression @C000005
  Scenario Outline: I see the arguments be used continuously
    Given I see main activity page
    Given I type <arg1> to first argument
    Given I type <arg2> to second argument
    When I tap on add button
    Then I see the text of result is <add_result>
    When I tap on sub button
    Then I see the text of result is <sub_result>
    When I tap on div button
    Then I see the text of result is <div_result>
    When I tap on mul button
    Then I see the text of result is <mul_result>

    Examples:
      | arg1 | arg2 | add_result | sub_result | div_result | mul_result |
      | 2    | 4    | 6          | -2         | 0.5        | 8          |
      | 4    | 2    | 6          | 2          | 2          | 8          |

  @regression @C000006
  Scenario: I could change arguments during calculation
    Given I see main activity page
    When I type 1 to first argument
    And I type 2 to second argument
    And I clear text and type 3 to first argument
    And I tap on add button
    Then I see the text of result is 5
    When I clear text and type 3 to second argument
    And I tap on div button
    Then I see the text of result is 1
    When I clear text and type 30 to first argument
    And I tap on mul button
    Then I see the text of result is 90
    When I clear text and type 5 to second argument
    And I tap on sub button
    Then I see the text of result is 25
