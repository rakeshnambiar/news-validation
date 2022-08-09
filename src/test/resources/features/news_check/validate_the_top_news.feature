Feature: Validate and prevent the fake news
  As a user of Guardian news
  I wanted to make sure the news I am reading is valid

  @Regression
  Scenario: Verify the authenticity of one of the top three news article by querying various ways
    Given Mike is navigated to the Guardian news home page
    When he selected the one of the top 3 articles
    And he noted down news headlines of the selected item
    And he decided to check the authenticity of these top news on "https://google.co.uk"
    And with some formatting he tried some searches on google website
    Then he should see similar news reported from at least 2 others sources on the Google search results

  @Sanity
  Scenario: Verify the authenticity of one of the article with exact search
    Given Mike is navigated to the Guardian news home page
    When he selected the one of the top 20 articles
    And he noted down news headlines of the selected item
    And he decided to check the authenticity of these top news on "https://google.co.uk"
    And without doing any formatting he tried to do a search on google website
    Then he should able to see at least 1 exact match on the Google search results
