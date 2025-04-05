# android-coding-challenge

Currently the simple app fetches a list of countries and displays them.

Documentation for used API: https://restcountries.com/

Your task is to continue development reaching the following milestones: 

- list should show only European countries
- list should show each countries capital too as well as its flag
- the layout of the item should show the flag on the left of texts and look OK
- upon clicking an item a detail screen should open showing population and area additionally

Emphasize quality and testing, this should be a work sample that meets your professional standards.
Leave comments for what you leave for later but deem important or any assumptions you make. 

Also feel free to use this file to add general notes or whatever you would like to document. 

Reserve 2-3 hours or less for this task (maybe you have reference projects handy). 
If more time is needed you might go overboard. Remember you can also comment open tasks. 

# Candidate Space

### Topics I Would Like to Implement Later:
- **Pull to Refresh**: Add functionality to refresh the screen and fetch data in case of failure to fetch data from the API.
- **Localization**: Implement support for the German language in the app.
- **Local Data Source**: Implement Room database to avoid multiple API calls and store data locally.
- **End-to-End Tests**: Implement End-to-End (E2E) and UI tests.
- **Reusable Design Components**: Develop reusable UI components to maintain consistency across screens.
- **Paging**: Implement paging to load data in pages from the API.
- **User Convenience**: Implement searching, sorting, and filtering functionality for the users.

---

## Testing Approach

The testing approach follows the **Test Pyramid** principle, which emphasizes having fewer E2E tests and more unit tests for better maintainability and reliability.

### E2E Tests
These tests simulate real user journeys and interactions within the app.

**User Journeys:**
1. **Launch and View List**:  
   User launches the app → List of European countries is displayed → User scrolls to the end → User finds information about the selected country.

2. **API Call Failure**:  
   User launches the app → Proper error message is displayed if the API call fails or there is a network failure.

3. **Navigate to Detail Screen**:  
   User launches the app → List of European countries is displayed → User clicks on a country card → Detailed country information is shown → User clicks the back button → The list of European countries is shown again.

---

### Unit Tests
Unit tests are written for individual components to ensure their correctness.

**Classes Covered by Unit Tests:**
1. `CountryListViewModel`
2. `CountryDetailViewModel`
3. `CountryRepository`
4. `RemoteDataSource`
5. `CountryMapper`

---

### Integration Tests
Integration tests validate the integration of multiple components working together.

1. **CompanyRepositoryIntegrationTest**:  
   This test validates the integration of `CountryRepository` and `RemoteDataSource`.

---

### UI Tests
These tests focus on verifying the UI and user interactions.

**UI Test Cases:**
1. The loading indicator should be displayed when the list is being fetched from the API.
2. The loading indicator should be hidden once the list is fetched successfully.
3. The list screen should be scrollable to view all countries until the end of the list.
4. Each list item should display the country flag, name, and capital.
5. The list screen should show only European countries.
6. The list screen should display the flag to the left of the country name and capital.
7. The list view should be responsive to different screen sizes.
8. The detail screen should be responsive to different screen sizes.
9. Clicking anywhere on a list item should navigate to the detail screen.
10. The detail screen should display the population and area of the selected country.
11. Clicking the back button on the detail screen should navigate back to the list screen.
12. The loading indicator should appear when the list is being fetched from the API.
13. An error message should appear if list fetching fails due to an API issue.
14. A proper error message should appear if list fetching fails due to a network error.
15. A proper error message should appear if list fetching fails due to an unknown error.
16. The back button should remain available on the detail screen when the fetching fails from the API.
17. Check the app in lite and dark mode
---

This approach ensures that the app is robust, maintainable, and user-friendly while covering various potential edge cases.
...