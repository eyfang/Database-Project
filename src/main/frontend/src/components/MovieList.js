import React, { useState, useEffect } from 'react';
import axios from 'axios';
import {
    Card,
    CardContent,
    Typography,
    Grid,
    Container,
    CircularProgress,
    TextField,
    Box,
    IconButton,
    Button,
    Snackbar,
    Alert
} from '@mui/material';
import SearchIcon from '@mui/icons-material/Search';
import ReviewDialog from './ReviewDialog';
import Dialog from '@mui/material/Dialog';
import DialogTitle from '@mui/material/DialogTitle';
import DialogContent from '@mui/material/DialogContent';
import DialogActions from '@mui/material/DialogActions';

function MovieList() {
    const [movies, setMovies] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [searchTerm, setSearchTerm] = useState('');
    const [selectedMovie, setSelectedMovie] = useState(null);
    const [reviewDialogOpen, setReviewDialogOpen] = useState(false);
    const [sortBy, setSortBy] = useState('title');
    const [sortOrder, setSortOrder] = useState('asc');
    const [detailsModalOpen, setDetailsModalOpen] = useState(false);
    const [snackbarOpen, setSnackbarOpen] = useState(false);
    const [snackbarMessage, setSnackbarMessage] = useState('');


    useEffect(() => {
        const fetchMovies = async () => {
            try {
                setLoading(true);
                const response = await axios.get('http://localhost:8080/api/movies');
                console.log('Response:', response.data);
                setMovies(response.data);
                setError(null);
            } catch (err) {
                console.error('Error details:', err);
                setError(err.message + (err.response?.data ? `: ${err.response.data}` : ''));
            } finally {
                setLoading(false);
            }
        };

        fetchMovies();
    }, []);

    const handleSnackbarClose = () => {
        setSnackbarOpen(false);
    };

    const handleSort = (newSortBy) => {
        const newSortOrder = sortBy === newSortBy && sortOrder === 'asc' ? 'desc' : 'asc';
        setSortBy(newSortBy);
        setSortOrder(newSortOrder);

        const sortedMovies = [...movies].sort((a, b) => {
            if (newSortBy === 'title') {
                return sortOrder === 'asc' ?
                    a.title.localeCompare(b.title) :
                    b.title.localeCompare(a.title);
            } else if (newSortBy === 'year') {
                return sortOrder === 'asc' ?
                    a.year - b.year :
                    b.year - a.year;
            }
            return 0;
        });

        setMovies(sortedMovies);
    };

    const handleSearch = async () => {
        try {
            setLoading(true);
            const response = await axios.get(`http://localhost:8080/api/movies/search?title=${searchTerm}`);
            setMovies(response.data);
        } catch (err) {
            setError(err.message);
        } finally {
            setLoading(false);
        }
    };

    const handleShowReviews = (movie) => {
        setSelectedMovie(movie);
        setReviewDialogOpen(true);
    };

    const handleAddToWatchlist = async (movieId) => {
        try {
            const user = JSON.parse(localStorage.getItem('user'));
            await axios.post('http://localhost:8080/api/watchlists', {
                userId: user.userId,
                movieId: movieId
            });
            setSnackbarMessage('Movie added to watchlist!');
            setSnackbarOpen(true);
        } catch (error) {
            setSnackbarMessage('Failed to add to watchlist');
            setSnackbarOpen(true);
        }
    };

    if (loading) {
        return (
            <Container style={{ display: 'flex', justifyContent: 'center', marginTop: '2rem' }}>
                <CircularProgress />
            </Container>
        );
    }

    if (error) {
        return (
            <Container>
                <Typography color="error" variant="h6" style={{ marginTop: '2rem' }}>
                    Error loading movies: {error}
                </Typography>
            </Container>
        );
    }

    return (
        <Container>
            <Typography variant="h4" component="h1" gutterBottom style={{ marginTop: '2rem' }}>
                Movies ({movies.length})
            </Typography>
            <Box sx={{ display: 'flex', gap: 1, mb: 3, mt: 2 }}>
                <TextField
                    fullWidth
                    label="Search movies"
                    variant="outlined"
                    value={searchTerm}
                    onChange={(e) => setSearchTerm(e.target.value)}
                    onKeyPress={(e) => e.key === 'Enter' && handleSearch()}
                />
                <IconButton onClick={handleSearch} color="primary">
                    <SearchIcon />
                </IconButton>
            </Box>
            <Box sx={{ mb: 2, display: 'flex', gap: 1 }}>
                <Button
                    variant={sortBy === 'title' ? 'contained' : 'outlined'}
                    onClick={() => handleSort('title')}
                    endIcon={sortBy === 'title' ? (sortOrder === 'asc' ? '↑' : '↓') : null}
                >
                    Sort by Title
                </Button>
                <Button
                    variant={sortBy === 'year' ? 'contained' : 'outlined'}
                    onClick={() => handleSort('year')}
                    endIcon={sortBy === 'year' ? (sortOrder === 'asc' ? '↑' : '↓') : null}
                >
                    Sort by Year
                </Button>
            </Box>

            <Grid container spacing={3}>
                {movies && movies.map(movie => (
                    <Grid item xs={12} sm={6} md={4} key={movie.movieId}>
                        <Card>
                            <CardContent>
                                <Typography
                                    variant="h6"
                                    component="h2"
                                    sx={{ cursor: 'pointer' }}
                                    onClick={() => {
                                        setSelectedMovie(movie);
                                        setDetailsModalOpen(true);
                                    }}
                                >
                                    {movie.title || 'Untitled'}
                                </Typography>
                                <Typography color="textSecondary">
                                    Year: {movie.year || 'N/A'}
                                </Typography>
                                {movie.director?.person?.name && (
                                    <Typography color="textSecondary">
                                        Director: {movie.director.person.name}
                                    </Typography>
                                )}
                                {movie.studio?.studioName && (
                                    <Typography color="textSecondary">
                                        Studio: {movie.studio.studioName}
                                    </Typography>
                                )}
                                <Box sx={{ mt: 2, display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                                    <Box>
                                        <Typography color="primary">
                                            Rating: {movie.rating || 'No ratings yet'}
                                        </Typography>
                                    </Box>
                                    <Box sx={{ display: 'flex', gap: 1 }}>
                                        <Button
                                            size="small"
                                            variant="outlined"
                                            onClick={() => handleAddToWatchlist(movie.movieId)}
                                        >
                                            Add to Watchlist
                                        </Button>
                                        <Button
                                            size="small"
                                            variant="contained"
                                            onClick={() => handleShowReviews(movie)}
                                        >
                                            See Reviews
                                        </Button>
                                    </Box>
                                </Box>
                            </CardContent>
                        </Card>
                    </Grid>
                ))}
            </Grid>

            <ReviewDialog
                open={reviewDialogOpen}
                onClose={() => setReviewDialogOpen(false)}
                movieId={selectedMovie?.movieId}
                movieTitle={selectedMovie?.title}
            />

            <Dialog
                open={detailsModalOpen}
                onClose={() => setDetailsModalOpen(false)}
                maxWidth="md"
                fullWidth
            >
                <DialogTitle>
                    {selectedMovie?.title} ({selectedMovie?.year})
                </DialogTitle>
                <DialogContent>
                    <Box sx={{ mt: 2 }}>
                        <Typography variant="h6">Details</Typography>
                        <Typography>Director: {selectedMovie?.director?.person?.name}</Typography>
                        <Typography>Studio: {selectedMovie?.studio?.studioName}</Typography>
                        <Typography>Writer: {selectedMovie?.writer?.person?.name}</Typography>

                        <Typography variant="h6" sx={{ mt: 2 }}>Average Rating</Typography>
                        <Typography>{selectedMovie?.rating || 'No ratings yet'}</Typography>
                    </Box>
                </DialogContent>
                <DialogActions>
                    <Button onClick={() => setDetailsModalOpen(false)}>Close</Button>
                </DialogActions>
            </Dialog>
            <Snackbar
                open={snackbarOpen}
                autoHideDuration={6000}
                onClose={handleSnackbarClose}
            >
                <Alert
                    onClose={handleSnackbarClose}
                    severity="success"
                    sx={{ width: '100%' }}
                >
                    {snackbarMessage}
                </Alert>
            </Snackbar>
        </Container>
    );
}

export default MovieList;