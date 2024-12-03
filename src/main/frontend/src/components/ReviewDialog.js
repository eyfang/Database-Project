import React, { useState, useEffect } from 'react';
import {
    Dialog,
    DialogTitle,
    DialogContent,
    DialogActions,
    Button,
    TextField,
    Rating as MuiRating,
    Typography,
    Box,
    List,
    ListItem,
    ListItemText,
    Divider
} from '@mui/material';
import axios from 'axios';

function ReviewDialog({ open, onClose, movieId, movieTitle, onMovieUpdate }) {
    const [reviews, setReviews] = useState([]);
    const [newReview, setNewReview] = useState('');
    const [rating, setRating] = useState(0);
    const [loading, setLoading] = useState(false);

    useEffect(() => {
        if (open && movieId) {
            fetchReviews();
        }
    }, [open, movieId]);

    const currentUser = JSON.parse(localStorage.getItem('user'));

    const fetchReviews = async () => {
        try {
            const response = await axios.get(`http://localhost:8080/api/reviews/movie/${movieId}`);
            console.log('Reviews from server:', response.data);
            setReviews(response.data);
        } catch (error) {
            console.error('Error fetching reviews:', error);
        }
    };

    const reviewsList = Array.isArray(reviews) ? reviews : [];

    const handleSubmitReview = async () => {
        try {
            const user = JSON.parse(localStorage.getItem('user'));
            if (!user) {
                alert('Please login to submit a review');
                return;
            }

            setLoading(true);
            await axios.post('http://localhost:8080/api/reviews', {
                userId: user.userId,
                movieId: movieId,
                reviewText: newReview,
                rating: rating
            });

            const updatedMovieResponse = await axios.get(`http://localhost:8080/api/movies/${movieId}/refresh`);
            if (onMovieUpdate) {
                onMovieUpdate(updatedMovieResponse.data);
            }

            setNewReview('');
            setRating(0);
            fetchReviews();
        } catch (error) {
            console.error('Error submitting review:', error);
        } finally {
            setLoading(false);
        }
    };

    const handleDeleteReview = async (reviewId) => {
        try {
            const user = JSON.parse(localStorage.getItem('user'));
            if (!user) {
                alert('Please login to delete a review');
                return;
            }

            await axios.delete(`http://localhost:8080/api/reviews/${reviewId}`, {
                params: { userId: user.userId }
            });
            fetchReviews();
            const updatedMovieResponse = await axios.get(`http://localhost:8080/api/movies/${movieId}/refresh`);
            if (onMovieUpdate) {
                onMovieUpdate(updatedMovieResponse.data);
            }
        } catch (error) {
            console.error('Error deleting review:', error);
            alert('Failed to delete review.');
        }
    };


    return (
        <Dialog open={open} onClose={onClose} maxWidth="md" fullWidth>
            <DialogTitle>{movieTitle} - Reviews</DialogTitle>
            <DialogContent>
                <List>
                    {reviewsList.map((review) => (
                        <React.Fragment key={review.reviewId}>
                            <ListItem>
                                <ListItemText
                                    primary={
                                        <Box display="flex" justifyContent="space-between" alignItems="center">
                                            <Typography>{review.user?.name}</Typography>
                                            <Box>
                                                <MuiRating
                                                    value={parseInt(review.ratingValue) || 0}
                                                    readOnly
                                                    size="small"
                                                />
                                                <Typography variant="caption">
                                                    (Rating value: {review.ratingValue || 'No rating'})
                                                </Typography>
                                            </Box>
                                        </Box>
                                    }
                                    secondary={
                                        <Box>
                                            <Typography component="span" variant="body2">
                                                {review.reviewText}
                                            </Typography>
                                            <Typography variant="caption" display="block" color="textSecondary">
                                                {new Date(review.date).toLocaleDateString()}
                                            </Typography>
                                        </Box>
                                    }
                                />
                                {review.userId === currentUser?.userId && (
                                    <Button
                                        variant="outlined"
                                        color="secondary"
                                        onClick={() => handleDeleteReview(review.reviewId)}
                                    >
                                        Delete
                                    </Button>
                                )}
                            </ListItem>
                            <Divider />
                        </React.Fragment>
                    ))}
                </List>

                <Box sx={{ mt: 3 }}>
                    <Typography variant="h6">Write a Review</Typography>
                    <Box sx={{ mb: 2 }}>
                        <MuiRating
                            value={rating}
                            onChange={(event, newValue) => {
                                setRating(newValue);
                            }}
                        />
                    </Box>
                    <TextField
                        fullWidth
                        multiline
                        rows={4}
                        value={newReview}
                        onChange={(e) => setNewReview(e.target.value)}
                        placeholder="Write your review here..."
                    />
                </Box>
            </DialogContent>
            <DialogActions>
                <Button onClick={onClose} color="inherit">Cancel</Button>
                <Button
                    onClick={handleSubmitReview}
                    color="primary"
                    disabled={loading || !newReview.trim() || !rating}
                >
                    Submit Review
                </Button>
            </DialogActions>
        </Dialog>
    );
}

export default ReviewDialog;