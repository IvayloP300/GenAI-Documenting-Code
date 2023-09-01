package com.softserve.taf.services.placeholder.endpoints;

import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.softserve.taf.models.enums.HttpStatus;
import com.softserve.taf.models.placeholder.comment.CommentDto;
import com.softserve.taf.services.common.AbstractWebEndpoint;

/**
 * The CommentEndpoint class provides methods to interact with the comments endpoint of an API.
 */
public class CommentEndpoint extends AbstractWebEndpoint {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String COMMENTS_END = "/comments";
    private static final String COMMENTS_RESOURCE_END = "/comments/{commentID}";

    /**
     * Constructor for initializing the CommentEndpoint with a specific request specification.
     *
     * Usage Example:
     * <pre>{@code
     * RequestSpecification spec = new RequestSpecBuilder().build();
     * CommentEndpoint endpoint = new CommentEndpoint(spec);
     * }</pre>
     *
     * @param specification The request specification to use for API interactions.
     */
    public CommentEndpoint(RequestSpecification specification) {
        super(specification);
    }

    /**
     * Creates a new comment using the provided CommentDto object.
     * Expects a CREATED HTTP status in response.
     *
     * Usage Example:
     * <pre>{@code
     * CommentDto newComment = new CommentDto("authorName", "commentBody");
     * CommentEndpoint endpoint = new CommentEndpoint(requestSpec);
     * CommentDto createdComment = endpoint.create(newComment);
     * }</pre>
     *
     * @param commentDto The comment data to create.
     * @return A CommentDto object of the created comment.
     */
    public CommentDto create(CommentDto commentDto) {
        return create(commentDto, HttpStatus.CREATED)
            .extract().as(CommentDto.class);
    }

    /**
     * Creates a new comment and validates the response status.
     *
     * Usage Example:
     * <pre>{@code
     * CommentDto newComment = new CommentDto("authorName", "commentBody");
     * CommentEndpoint endpoint = new CommentEndpoint(requestSpec);
     * ValidatableResponse response = endpoint.create(newComment, HttpStatus.CREATED);
     * response.assertThat().body("author", equalTo("authorName"));
     * }</pre>
     *
     * @param commentDto The comment data to create.
     * @param status The expected HTTP status of the response.
     * @return A ValidatableResponse object for further validation.
     */
    public ValidatableResponse create(CommentDto commentDto, HttpStatus status) {
        LOGGER.info("Create new Comment");
        return post(
            this.specification,
            COMMENTS_END,
            commentDto)
            .statusCode(status.getCode());
    }

    /**
     * Updates an existing comment by its ID using the provided CommentDto object.
     * Expects an OK HTTP status in response.
     *
     * Usage Example:
     * <pre>{@code
     * CommentDto updatedComment = new CommentDto("updatedAuthorName", "updatedCommentBody");
     * CommentEndpoint endpoint = new CommentEndpoint(requestSpec);
     * CommentDto result = endpoint.update(10, updatedComment);
     * }</pre>
     *
     * @param id The ID of the comment to update.
     * @param commentDto The comment data to update with.
     * @return A CommentDto object of the updated comment.
     */
    public CommentDto update(int id, CommentDto commentDto) {
        return update(commentDto, id, HttpStatus.OK)
            .extract().as(CommentDto.class);
    }

    /**
     * Updates an existing comment by its ID and validates the response status.
     *
     * Usage Example:
     * <pre>{@code
     * CommentDto updatedComment = new CommentDto("updatedAuthorName", "updatedCommentBody");
     * CommentEndpoint endpoint = new CommentEndpoint(requestSpec);
     * ValidatableResponse response = endpoint.update(updatedComment, 10, HttpStatus.OK);
     * response.assertThat().body("author", equalTo("updatedAuthorName"));
     * }</pre>
     *
     * @param commentDto The comment data to update with.
     * @param id The ID of the comment to update.
     * @param status The expected HTTP status of the response.
     * @return A ValidatableResponse object for further validation.
     */
    public ValidatableResponse update(CommentDto commentDto, int id, HttpStatus status) {
        LOGGER.info("Update Comment by id [{}]", id);
        return put(
            this.specification,
            COMMENTS_RESOURCE_END,
            commentDto,
            id)
            .statusCode(status.getCode());
    }

    /**
     * Retrieves a comment by its ID.
     * Expects an OK HTTP status in response.
     *
     * Usage Example:
     * <pre>{@code
     * CommentEndpoint endpoint = new CommentEndpoint(requestSpec);
     * CommentDto fetchedComment = endpoint.getById(5);
     * }</pre>
     *
     * @param id The ID of the comment to be retrieved.
     * @return A CommentDto object of the retrieved comment.
     */
    public CommentDto getById(int id) {
        return getById(id, HttpStatus.OK)
            .extract().as(CommentDto.class);
    }

    /**
     * Retrieves a comment by its ID and validates the response status.
     *
     * Usage Example:
     * <pre>{@code
     * CommentEndpoint endpoint = new CommentEndpoint(requestSpec);
     * ValidatableResponse response = endpoint.getById(5, HttpStatus.OK);
     * response.assertThat().body("id", equalTo(5));
     * }</pre>
     *
     * @param id The ID of the comment to be retrieved.
     * @param status The expected HTTP status of the response.
     * @return A ValidatableResponse object for further validation.
     */
    public ValidatableResponse getById(int id, HttpStatus status) {
        LOGGER.info("Get Comment by id [{}]", id);
        return get(
            this.specification,
            COMMENTS_RESOURCE_END,
            String.valueOf(id))
            .statusCode(status.getCode());
    }

    /**
     * Retrieves all comments from the API.
     *
     * Usage Example:
     * <pre>{@code
     * CommentEndpoint endpoint = new CommentEndpoint(requestSpec);
     * List<CommentDto> allComments = endpoint.getAll();
     * }</pre>
     *
     * @return A List of CommentDto objects representing all comments.
     */
    public List<CommentDto> getAll() {
        return List.of(getAll(HttpStatus.OK).extract().as(CommentDto[].class));
    }

    /**
     * Retrieves all comments from the API and validates the response status.
     *
     * Usage Example:
     * <pre>{@code
     * CommentEndpoint endpoint = new CommentEndpoint(requestSpec);
     * ValidatableResponse allCommentsResponse = endpoint.getAll(HttpStatus.OK);
     * allCommentsResponse.assertThat().body("size()", greaterThan(0));
     * }</pre>
     *
     * @param status The expected HTTP status of the response.
     * @return A ValidatableResponse object for further validation.
     */
    public ValidatableResponse getAll(HttpStatus status) {
        LOGGER.info("Get all Comments");
        ValidatableResponse response = get(this.specification, COMMENTS_END);
        response.statusCode(status.getCode());
        return response;
    }
}