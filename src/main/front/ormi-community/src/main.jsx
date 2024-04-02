import React from 'react'
import ReactDOM from 'react-dom/client'
import './index.css'
import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import EditPassword from './routes/Password/EditPassword';
import DocumentWrite from './routes/Document/DocumentWrite';
import { Comment } from './components/Comment/Comment';
import { Document } from './components/Document/Document';
import Header from './components/Layout/Header';
import Main from './routes/Main/Main';
import Signup from './routes/Signup/Signup';
import BoardCreate from './routes/Board/BoardCreate';
import EditInformation from './routes/User/EditInformation';
import Login from './routes/Login';
import SignupComplete from './routes/Signup/SignupComplete';
import FindPassword from './routes/Password/FindPassword';
import Admin from './routes/admin/Admin';
import AdminBoard from './routes/Board/AdminBoard';

const router = createBrowserRouter([
      {
        path: "/",
        element: <Main></Main>
      },
      {
        path: "/login",
        element: <Login></Login>
      },
      {
        path: "/board",
        element: <BoardCreate></BoardCreate>
      },
      {
        path: "/document",
        element: <DocumentWrite></DocumentWrite>
      },
      {
        path: "/signup",
        element: <Signup></Signup>,
          
      },
      {
        path: "/signup/complete",
        element: <SignupComplete></SignupComplete>
      },
      {
        path: "/user",
        element: <EditInformation></EditInformation>,
      },
      {
        path: "/password",
        element: <FindPassword></FindPassword>
      },
      {
        path: "/password/edit",
        element: <EditPassword></EditPassword>
      },
      {
        path: "/admin",
        element: <Admin></Admin>,
        children : [
          {
            path: "/admin/board",
            element: <AdminBoard></AdminBoard>
          }
        ]
      }
]);

ReactDOM.createRoot(document.getElementById('root')).render(
  <RouterProvider router={router} />
)
