window.addEventListener("DOMContentLoaded", (event) => {
    //#region //(ok) Get element du DOM
    const btns_Open = document.querySelectorAll("#btnOpen"); //--> (0-5) 6 buttons submits Open armoir
    const btns_Close = document.querySelectorAll("#btnClose"); //--> (0-5)6 buttons submits Close armoir
    const body = document.getElementById("bodyTR");
    //#endregion
  
    
    //#region //(ok) Bind de tout les btn Open
    btns_Open.forEach((button) => {
      bind_OpenClick(button);
    });
    //#endregion
  
  
    //#region //(ok) Bind de tout les btn Close
    btns_Close.forEach((button) => {
      bind_CloseClick(button);
    });
    //#endregion
  
  
  });
  
  // ------------------------------------- Fonction -----------------------------
  
  //#region -->(ok) Event click button submit
  const bind_OpenClick = (button) => {
    
    //#region -->(ok) Element du DOM recuperer
    const newTR = document.createElement("tr");
    const oldTR = button.parentNode.parentNode;
    const parentNodeTRs = oldTR.parentNode;
    //#endregion
  
  
    button.addEventListener("click", (event) => {
      axios
        .post("/simulateur", {
          type: button.name,      //send le id de l'armoir clicker open
          state: "open"           //Send open pour dire que c ouvert
        })
  
        .then((res) => {
          newTR.innerHTML = res.data;
          parentNodeTRs.replaceChild(newTR, oldTR);
         
          //#region -->Linker les new button Open / Close 
          newCloseButton = newTR.childNodes[3].childNodes[2];
          bind_CloseClick(newCloseButton);
         
          newOpenButton = newTR.childNodes[3].childNodes[0];
          bind_OpenClick(newOpenButton);
          //#endregion
        })
  
        .catch((err) => {
          console.log(err);
        });
    });
  };
  //#endregion
  
  
  //#region -->(ok) Event click button submit
  const bind_CloseClick = (button) => {
   
    //#region -->Element du DOM recuperer
    const newTR = document.createElement("tr");
    const oldTR = button.parentNode.parentNode;
    const parentNodeTRs = oldTR.parentNode;
    //#endregion
  
    button.addEventListener("click", (event) => {
      axios
        .post("/simulateur", {
          type: button.name,      //send le id de l'armoir clicker open
          state: "close"          //Send open pour dire que c ouvert
        })
  
        .then((res) => {
          newTR.innerHTML = res.data;
          parentNodeTRs.replaceChild(newTR, oldTR);
          
          //#region -->Linker les new button Open / Close
          newCloseButton = newTR.childNodes[3].childNodes[2];
          bind_CloseClick(newCloseButton);
          
          newOpenButton = newTR.childNodes[3].childNodes[0];
          bind_OpenClick(newOpenButton);
          //#endregion
        })
  
        .catch((err) => {
          console.log(err);
        });
    });
  };
  //#endregion