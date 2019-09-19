package com.trilogyed.post.dao;

import com.trilogyed.post.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class PostDaoJdbcTemplateImpl implements PostDao {
    private static final String INSERT_POST_SQL =
            "insert into post (post_date,poster_name,post) values (?, ?, ?)";

    private static final String SELECT_POST_SQL =
            "select * from post where post_id = ?";

    private static final String SELECT_POST_BY_NAME_SQL =
            "select * from post where poster_name = ?";

    private static final String SELECT_ALL_POSTS_SQL =
            "select * from post";

    private static final String DELETE_POST_SQL =
            "delete from post where post_id = ?";

    private static final String UPDATE_POST_SQL =
            "update post set post_date =? ,poster_name = ?,post = ? where post_id = ?";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public PostDaoJdbcTemplateImpl(JdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public Post createPost(Post post) {
        jdbcTemplate.update(INSERT_POST_SQL,
                post.getPostDate(),
                post.getPosterName(),
                post.getPost());

        int id = jdbcTemplate.queryForObject("select last_insert_id()", Integer.class);

        post.setPostID(id);

        return post;
    }

    @Override
    public Post getPostById(int id) {
        try {

            return jdbcTemplate.queryForObject(SELECT_POST_SQL, this::mapRowToPost, id);

        } catch (EmptyResultDataAccessException e) {
            // if nothing is returned just catch the exception and return null
            return null;
        }
    }

    @Override
    public List<Post> getAllPosts() {
        return jdbcTemplate.query(SELECT_ALL_POSTS_SQL, this::mapRowToPost);
    }

    @Override
    public void updatePost(Post post) {
        jdbcTemplate.update(UPDATE_POST_SQL,


                post.getPostDate(),
                post.getPosterName(),
                post.getPost(),
                post.getPostID()

        );


    }
    public List<Post> getPostsByPosterName(String posterName){
        return jdbcTemplate.query(SELECT_POST_BY_NAME_SQL, this::mapRowToPost,posterName);
    }

    @Override
    public void deletePost(int id) {
        jdbcTemplate.update(DELETE_POST_SQL, id);
    }

    private Post mapRowToPost(ResultSet rs, int rowNum) throws SQLException {
        Post post = new Post();
        post.setPostID(rs.getInt("post_id"));
        post.setPostDate(rs.getDate("post_date").toLocalDate());
        post.setPosterName(rs.getString("poster_name"));
        post.setPost(rs.getString("post"));


        return post;
    }
}
