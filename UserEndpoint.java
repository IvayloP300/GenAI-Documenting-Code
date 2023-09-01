package com.softserve.taf.services.placeholder.endpoints;

import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.softserve.taf.models.enums.HttpStatus;
import com.softserve.taf.models.placeholder.user.UserDto;
import com.softserve.taf.services.common.AbstractWebEndpoint;

/**
 * The UserEndpoint class provides methods to interact with the user endpoint of an API.
 */
public class UserEndpoint extends AbstractWebEndpoint {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String USERS_END = "/users";
    private static final String USERS_RESOURCE_END = "/users/{userID}";

    /**
     * Constructor for initializing the UserEndpoint with a specific request specification.
     *
     * Usage Example:
     * <pre>{@code
     * RequestSpecification spec = new RequestSpecBuilder().build();
     * UserEndpoint endpoint = new UserEndpoint(spec);
     * }</pre>
     *
     * @param specification The request specification to use for API interactions.
     */
    public UserEndpoint(RequestSpecification specification) {
        super(specification);
    }

    /**
     * Creates a new user using the provided UserDto object.
     * Expects a CREATED HTTP status in response.
     *
     * Usage Example:
     * <pre>{@code
     * UserDto newUser = new UserDto("JohnDoe", "johndoe@example.com");
     * UserEndpoint endpoint = new UserEndpoint(requestSpec);
     * UserDto createdUser = endpoint.create(newUser);
     * }</pre>
     *
     * @param userDto The user data to create.
     * @return A UserDto object of the created user.
     */
    public UserDto create(UserDto userDto) {
        return create(userDto, HttpStatus.CREATED)
            .extract().as(UserDto.class);
    }

    /**
     * Creates a new user and validates the response status.
     *
     * Usage Example:
     * <pre>{@code
     * UserDto newUser = new UserDto("JohnDoe", "johndoe@example.com");
     * UserEndpoint endpoint = new UserEndpoint(requestSpec);
     * ValidatableResponse response = endpoint.create(newUser, HttpStatus.CREATED);
     * response.assertThat().body("username", equalTo("JohnDoe"));
     * }</pre>
     *
     * @param userDto The user data to create.
     * @param status The expected HTTP status of the response.
     * @return A ValidatableResponse object for further validation.
     */
    public ValidatableResponse create(UserDto userDto, HttpStatus status) {
        LOGGER.info("Create new User");
        return post(
            this.specification,
            USERS_END,
            userDto)
            .statusCode(status.getCode());
    }

    /**
     * Updates an existing user by its ID using the provided UserDto object.
     * Expects an OK HTTP status in response.
     *
     * Usage Example:
     * <pre>{@code
     * UserDto updatedUser = new UserDto("JohnSmith", "johnsmith@example.com");
     * UserEndpoint endpoint = new UserEndpoint(requestSpec);
     * UserDto result = endpoint.update(10, updatedUser);
     * }</pre>
     *
     * @param id The ID of the user to update.
     * @param userDto The user data to update with.
     * @return A UserDto object of the updated user.
     */
    public UserDto update(int id, UserDto userDto) {
        return update(userDto, id, HttpStatus.OK)
            .extract().as(UserDto.class);
    }

    /**
     * Updates an existing user by its ID and validates the response status.
     *
     * Usage Example:
     * <pre>{@code
     * UserDto updatedUser = new UserDto("JohnSmith", "johnsmith@example.com");
     * UserEndpoint endpoint = new UserEndpoint(requestSpec);
     * ValidatableResponse response = endpoint.update(updatedUser, 10, HttpStatus.OK);
     * response.assertThat().body("username", equalTo("JohnSmith"));
     * }</pre>
     *
     * @param userDto The user data to update with.
     * @param id The ID of the user to update.
     * @param status The expected HTTP status of the response.
     * @return A ValidatableResponse object for further validation.
     */
    public ValidatableResponse update(UserDto userDto, int id, HttpStatus status) {
        LOGGER.info("Update User by id [{}]", id);
        return put(
            this.specification,
            USERS_RESOURCE_END,
            userDto,
            id)
            .statusCode(status.getCode());
    }

    /**
     * Retrieves a user by its ID.
     * Expects an OK HTTP status in response.
     *
     * Usage Example:
     * <pre>{@code
     * UserEndpoint endpoint = new UserEndpoint(requestSpec);
     * UserDto fetchedUser = endpoint.getById("5");
     * }</pre>
     *
     * @param id The ID of the user to be retrieved.
     * @return A UserDto object of the retrieved user.
     */
    public UserDto getById(String id) {
        return getById(id, HttpStatus.OK)
            .extract().as(UserDto.class);
    }

    /**
     * Retrieves a user by its ID and validates the response status.
     *
     * Usage Example:
     * <pre>{@code
     * UserEndpoint endpoint = new UserEndpoint(requestSpec);
     * ValidatableResponse response = endpoint.getById("5", HttpStatus.OK);
     * response.assertThat().body("id", equalTo(5));
     * }</pre>
     *
     * @param id The ID of the user to be retrieved.
     * @param status The expected HTTP status of the response.
     * @return A ValidatableResponse object for further validation.
     */
    public ValidatableResponse getById(String id, HttpStatus status) {
        LOGGER.info("Get User by id [{}]", id);
        return get(
            this.specification,
            USERS_RESOURCE_END,
            id)
            .statusCode(status.getCode());
    }

    /**
     * Retrieves all users from the API.
     *
     * Usage Example:
     * <pre>{@code
     * UserEndpoint endpoint = new UserEndpoint(requestSpec);
     * List<UserDto> allUsers = endpoint.getAll();
     * }</pre>
     *
     * @return A List of UserDto objects representing all users.
     */
    public List<UserDto> getAll() {
        return List.of(getAll(HttpStatus.OK).extract().as(UserDto[].class));
    }

    /**
     * Retrieves all users from the API and validates the response status.
     *
     * Usage Example:
     * <pre>{@code
     * UserEndpoint endpoint = new UserEndpoint(requestSpec);
     * ValidatableResponse allUsersResponse = endpoint.getAll(HttpStatus.OK);
     * allUsersResponse.assertThat().body("size()", greaterThan(0));
     * }</pre>
     *
     * @param status The expected HTTP status of the response.
     * @return A ValidatableResponse object for further validation.
     */
    public ValidatableResponse getAll(HttpStatus status) {
        LOGGER.info("Get all Users");
        ValidatableResponse response = get(this.specification, USERS_END);
        response.statusCode(status.getCode());
        return response;
    }
}
