import React, { useState, useEffect } from 'react';
import {
    Card,
    CardContent,
    Typography,
    Grid,
    Container,
    CircularProgress,
    Button
} from '@mui/material';
import axios from 'axios';
import { Alert } from '@mui/material';
import Snackbar from '@mui/material/Snackbar';

function WatchList() {
    const [watchlist, setWatchlist] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [snackbarOpen, setSnackbarOpen] = useState(false);
    const [snackbarMessage, setSnackbarMessage] = useState('');

    useEffect(() => {
        const fetchWatchlist = async () => {
            try {
                const user = JSON.parse(localStorage.getItem('user'));
                if (!user) {
                    throw new Error('No user logged in');
                }
                const response = await axios.get(`http://localhost:8080/api/watchlists/user/${user.userId}`);
                setWatchlist(response.data);
                setLoading(false);
            } catch (error) {
                console.error('Error fetching watchlist:', error);
                setError(error.message);
                setLoading(false);
            }
        };

        fetchWatchlist();
    }, []);

    const handleSnackbarClose = () => {
        setSnackbarOpen(false);
    };

    const handleRemoveFromWatchlist = async (watchListId) => {
        try {
            await axios.delete(`http://localhost:8080/api/watchlists/${watchListId}`);
            setWatchlist(watchlist.filter(item => item.watchListId !== watchListId));
            setSnackbarMessage('Movie removed from watchlist');
            setSnackbarOpen(true);
        } catch (error) {
            console.error('Error removing from watchlist:', error);
            setSnackbarMessage('Failed to remove from watchlist');
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
                <Typography color="error" variant="h6">
                    Error loading watchlist: {error}
                </Typography>
            </Container>
        );
    }

    return (
        <Container>
            <Typography variant="h4" component="h1" gutterBottom style={{ marginTop: '2rem' }}>
                My Watchlist ({watchlist.length})
            </Typography>
            <Grid container spacing={3}>
                {watchlist.map(item => (
                    <Grid item xs={12} sm={6} md={4} key={item.watchListId}>
                        <Card>
                            <CardContent>
                                <Typography variant="h6">
                                    {item.movie?.title}
                                </Typography>
                                <Typography color="textSecondary">
                                    Year: {item.movie?.year}
                                </Typography>
                                {item.movie?.director?.person?.name && (
                                    <Typography color="textSecondary">
                                        Director: {item.movie.director.person.name}
                                    </Typography>
                                )}
                                <Button
                                    variant="outlined"
                                    color="error"
                                    size="small"
                                    sx={{ mt: 2 }}
                                    onClick={() => handleRemoveFromWatchlist(item.watchListId)}
                                >
                                    Remove from Watchlist
                                </Button>
                            </CardContent>
                        </Card>
                    </Grid>
                ))}
            </Grid>
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

export default WatchList;