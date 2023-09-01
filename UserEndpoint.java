/**
 * Represents the endpoint functionalities for users.
 * Provides methods to create, update, retrieve, and list users.
 *
 * <p>Example Usage:
 * <pre>
 * RequestSpecification spec = ...; // Obtain a request specification
 * UserEndpoint endpoint = new UserEndpoint(spec);
 * UserDto newUser = new UserDto(...); // Create a user DTO
 * endpoint.create(newUser);
 * </pre>
 */
public class UserEndpoint extends AbstractWebEndpoint {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String USERS_END = "/users";
    private static final String USERS_RESOURCE_END = "/users/{userID}";

    public UserEndpoint(RequestSpecification specification) {
        super(specification);
    }

    /**
     * Creates a new user.
     *
     * @param userDto The data of the user to be created.
     * @return A DTO representing the created user.
     *
     * <p>Example:
     * <pre>
     * UserDto user = new UserDto(...); // Provide the necessary details
     * UserDto createdUser = endpoint.create(user);
     * </pre>
     */
    public UserDto create(UserDto userDto) {
        return create(userDto, HttpStatus.CREATED).extract().as(UserDto.class);
    }

    public ValidatableResponse create(UserDto userDto, HttpStatus status) {
        LOGGER.info("Create new User");
        return post(this.specification, USERS_END, userDto).statusCode(status.getCode());
    }

    /**
     * Updates an existing user by its ID.
     *
     * @param id       The ID of the user to be updated.
     * @param userDto  The updated data of the user.
     * @return A DTO representing the updated user.
     *
     * <p>Example:
     * <pre>
     * String userId = "123"; // ID of the user to update
     * UserDto updatedData = new UserDto(...); // Provide the new details
     * UserDto updatedUser = endpoint.update(userId, updatedData);
     * </pre>
     */
    public UserDto update(int id, UserDto userDto) {
        return update(userDto, id, HttpStatus.OK).extract().as(UserDto.class);
    }

    public ValidatableResponse update(UserDto userDto, int id, HttpStatus status) {
        LOGGER.info("Update User by id [{}]", id);
        return put(this.specification, USERS_RESOURCE_END, userDto, id).statusCode(status.getCode());
    }

    /**
     * Retrieves a user by its ID.
     *
     * @param id The ID of the user to be retrieved.
     * @return A DTO representing the retrieved user.
     *
     * <p>Example:
     * <pre>
     * String userId = "123"; // ID of the user to retrieve
     * UserDto retrievedUser = endpoint.getById(userId);
     * </pre>
     */
    public UserDto getById(String id) {
        return getById(id, HttpStatus.OK).extract().as(UserDto.class);
    }

    public ValidatableResponse getById(String id, HttpStatus status) {
        LOGGER.info("Get User by id [{}]", id);
        return get(this.specification, USERS_RESOURCE_END, id).statusCode(status.getCode());
    }

    /**
     * Retrieves all users.
     *
     * @return A list of DTOs representing all users.
     *
     * <p>Example:
     * <pre>
     * List<UserDto> allUsers = endpoint.getAll();
     * allUsers.forEach(user -> System.out.println(user.getName()));
     * </pre>
     */
    public List<UserDto> getAll() {
        return List.of(getAll(HttpStatus.OK).extract().as(UserDto[].class));
    }

    public ValidatableResponse getAll(HttpStatus status) {
        LOGGER.info("Get all Users");
        ValidatableResponse response = get(this.specification, USERS_END);
        response.statusCode(status.getCode());
        return response;
    }
}