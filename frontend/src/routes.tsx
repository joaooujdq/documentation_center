import { Route, BrowserRouter } from  'react-router-dom';
import ManageUsers from 'pages/manageUsers';
import ManageBranchs from 'pages/manageBranchs';
import ManageFolders from 'pages/manageFolders';
import ManageCards from 'pages/manageCards';
import NewUser from 'pages/newUser';
import NewFolder from 'pages/newFolder';
import NewBranch from 'pages/newBranch';
import NewCard from 'pages/newCard';
import UpdateUser from 'pages/updateUser';
import UpdateCard from 'pages/updateCard';
import UpdateBranch from 'pages/updateBranch';
import UpdateFolder from 'pages/updateFolder';


const Routes = () => {
  return (
    <BrowserRouter>
      <Route component={ManageUsers} path="/manageusers" exact />
      <Route component={ManageBranchs} path="/managebranchs" exact/>
      <Route component={ManageFolders} path="/managefolders" exact/>
      <Route component={ManageCards} path="/" exact/>
      <Route component={NewUser} path="/newuser" exact />
      <Route component={NewFolder} path="/newfolder" exact/>
      <Route component={NewBranch} path="/newbranch" exact/>
      <Route component={NewCard} path="/newcard" exact/>
      <Route component={UpdateUser} path="/updateuser" exact/>
      <Route component={UpdateCard} path="/updatecard" exact/>
      <Route component={UpdateBranch} path="/updatebranch" exact/>
      <Route component={UpdateFolder} path="/updatefolder" exact/>
    </BrowserRouter>
  );
}
export default Routes;