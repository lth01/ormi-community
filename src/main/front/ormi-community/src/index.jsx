import { createContext, useEffect } from 'react';
import { useState } from 'react';

const GlobalContext = createContext({
  selectBoardID: "",
  setSelectBoardID: () =>{},
});

const ContextProvider = ({children}) =>{
    const [selectBoardID, setSelectBoardID] = useState("");


    return (
        <GlobalContext.Provider value={{
            selectBoardID,
            setSelectBoardID
        }}>
            {children}
        </GlobalContext.Provider>
    )
}


export {GlobalContext, ContextProvider}