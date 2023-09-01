/**
 * Represents the endpoint functionalities for comments.
 * Provides methods to create, update, retrieve, and list comments.
 *
 * <p>Example Usage:
 * <pre>
 * RequestSpecification spec = ...; // Obtain a request specification
 * CommentEndpoint endpoint = new CommentEndpoint(spec);
 * CommentDto newComment = new CommentDto(...); // Create a comment DTO
 * endpoint.create(newComment);
 * </pre>
 */
public class CommentEndpoint extends AbstractWebEndpoint {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String COMMENTS_END = "/comments";
    private static final String COMMENTS_RESOURCE_END = "/comments/{commentID}";

    public CommentEndpoint(RequestSpecification specification) {
        super(specification);
    }

    /**
     * Creates a new comment.
     *
     * @param commentDto The data of the comment to be created.
     * @return A DTO representing the created comment.
     *
     * <p>Example:
     * <pre>
     * CommentDto comment = new CommentDto(...); // Provide the necessary details
     * CommentDto createdComment = endpoint.create(comment);
     * </pre>
     */
    public CommentDto create(CommentDto commentDto) {
        return create(commentDto, HttpStatus.CREATED).extract().as(CommentDto.class);
    }

    public ValidatableResponse create(CommentDto commentDto, HttpStatus status) {
        LOGGER.info("Create new Comment");
        return post(this.specification, COMMENTS_END, commentDto).statusCode(status.getCode());
    }

    /**
     * Updates an existing comment by its ID.
     *
     * @param id          The ID of the comment to be updated.
     * @param commentDto  The updated data of the comment.
     * @return A DTO representing the updated comment.
     *
     * <p>Example:
     * <pre>
     * int commentId = 123; // ID of the comment to update
     * CommentDto updatedData = new CommentDto(...); // Provide the new details
     * CommentDto updatedComment = endpoint.update(commentId, updatedData);
     * </pre>
     */
    public CommentDto update(int id, CommentDto commentDto) {
        return update(commentDto, id, HttpStatus.OK).extract().as(CommentDto.class);
    }

    public ValidatableResponse update(CommentDto commentDto, int id, HttpStatus status) {
        LOGGER.info("Update Comment by id [{}]", id);
        return put(this.specification, COMMENTS_RESOURCE_END, commentDto, id).statusCode(status.getCode());
    }

    /**
     * Retrieves a comment by its ID.
     *
     * @param id The ID of the comment to be retrieved.
     * @return A DTO representing the retrieved comment.
     *
     * <p>Example:
     * <pre>
     * int commentId = 123; // ID of the comment to retrieve
     * CommentDto retrievedComment = endpoint.getById(commentId);
     * </pre>
     */
    public CommentDto getById(int id) {
        return getById(id, HttpStatus.OK).extract().as(CommentDto.class);
    }

    public ValidatableResponse getById(int id, HttpStatus status) {
        LOGGER.info("Get Comment by id [{}]", id);
        return get(this.specification, COMMENTS_RESOURCE_END, String.valueOf(id)).statusCode(status.getCode());
    }

    /**
     * Retrieves all comments.
     *
     * @return A list of DTOs representing all comments.
     *
     * <p>Example:
     * <pre>
     * List<CommentDto> allComments = endpoint.getAll();
     * allComments.forEach(comment -> System.out.println(comment.getContent()));
     * </pre>
     */
    public List<CommentDto> getAll() {
        return List.of(getAll(HttpStatus.OK).extract().as(CommentDto[].class));
    }

    public ValidatableResponse getAll(HttpStatus status) {
        LOGGER.info("Get all Comments");
        ValidatableResponse response = get(this.specification, COMMENTS_END);
        response.statusCode(status.getCode());
        return response;
    }
}