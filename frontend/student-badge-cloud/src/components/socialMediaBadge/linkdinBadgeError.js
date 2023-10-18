import { Box, Button, Container, makeStyles } from '@material-ui/core';
import React from 'react';

const useStyles = makeStyles((theme) => ({
    errorPage: {
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
        justifyContent: "center",
        height: "100vh",
        backgroundColor: "#f5f5f5",
        fontFamily: "Arial, sans-serif",
    },
    errorHeading: {
        fontSize: "36px",
        fontWeight: "bold",
        marginBottom: "20px",
        color: "#444",
    },
    errorMessage: {
        fontSize: "18px",
        lineHeight: "1.5",
        textAlign: "center",
        color: "#777",
        maxWidth: "500px",
        margin: "0 auto",
        padding: "0 20px",
    },

}));
const LinkedInBadgeError = () => {
    const classes = useStyles();
    return (
        <div >
            <div className={classes.errorPage}>
                <h1 className={classes.errorHeading}>Oops, there was an error</h1>
                <p className={classes.errorMessage}>It seems that the LinkedIn badge failed to load. Please try again later or check your internet connection.</p>
                <Box mt={6} display='flex' justifyContent='center'>
                    <Button
                        color='secondary'
                        onClick={() => {
                            window.location.href = "/";
                        }}
                        variant='outlined'
                    >
                        Back to home
                    </Button>
                </Box>
            </div>
        </div>
    );
};

export default LinkedInBadgeError;