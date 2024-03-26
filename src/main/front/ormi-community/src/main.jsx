import React from 'react'
import ReactDOM from 'react-dom/client'
import './index.css'
import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import Document from './routes/Document.jsx';
import Root from './routes/Root.jsx';
import Menu from './components/Menu/Menu';
import Menus from './components/Menu/Menus';

const router = createBrowserRouter([
  {
      path: "",
      element: (
        <Menus></Menus>
      )
  },
  {
      path: "/doc",
      element: (
          <Document />
      )
  },
]);

ReactDOM.createRoot(document.getElementById('root')).render(
  <RouterProvider router={router} />
)
