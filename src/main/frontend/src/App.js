import React, { useState, useEffect } from 'react';
import {
    BrowserRouter as Router,
    Routes,
    Route,
    Navigate,
    Link
} from 'react-router-dom';
import {
    AppBar,
    Toolbar,
    Typography,
    CssBaseline,
    Container,
    Button,
    Box
} from '@mui/material';
import MovieList from './components/MovieList';
import WatchList from './components/WatchList';
import Login from './components/Login';
import Register from './components/Register';

function App() {
    const [user, setUser] = useState(null);

    useEffect(() => {
        const storedUser = localStorage.getItem('user');
        if (storedUser) {
            setUser(JSON.parse(storedUser));
        }
    }, []);

    const handleLogin = (userData) => {
        setUser(userData);
    };

    const handleLogout = () => {
        localStorage.removeItem('user');
        setUser(null);
    };

    return (
        <Router>
            <CssBaseline />
            <AppBar position="static">
                <Toolbar>
                    <Typography variant="h6" style={{ flexGrow: 1 }}>
                        Movie Review Application
                    </Typography>
                    {user ? (
                        <Box sx={{ display: 'flex', alignItems: 'center', gap: 2 }}>
                            <Typography>
                                Welcome, {user.name}
                            </Typography>
                            <Button color="inherit" component={Link} to="/">
                                Movies
                            </Button>
                            <Button color="inherit" component={Link} to="/watchlists">
                                Watchlists
                            </Button>
                            <Button color="inherit" onClick={handleLogout}>
                                Logout
                            </Button>
                        </Box>
                    ) : (
                        <Box>
                            <Button color="inherit" component={Link} to="/login">
                                Login
                            </Button>
                            <Button color="inherit" component={Link} to="/register">
                                Register
                            </Button>
                        </Box>
                    )}
                </Toolbar>
            </AppBar>
            <Container>
                <Routes>
                    <Route path="/login" element={
                        !user ? <Login onLogin={handleLogin} /> : <Navigate to="/" />
                    } />
                    <Route path="/register" element={
                        !user ? <Register onRegister={handleLogin} /> : <Navigate to="/" />
                    } />
                    <Route path="/" element={
                        user ? <MovieList /> : <Navigate to="/login" />
                    } />
                    <Route path="/watchlists" element={
                        user ? <WatchList /> : <Navigate to="/login" />
                    } />
                </Routes>
            </Container>
        </Router>
    );
}

export default App;