import styled from 'styled-components'
import Logo from '../common/Logo';
import Profile from '../common/Profile';
import IconButton from '@mui/material/IconButton';
import EmailOutlinedIcon from '@mui/icons-material/EmailOutlined';
import LogoutOutlinedIcon from '@mui/icons-material/LogoutOutlined';
import NotificationsNoneOutlinedIcon from '@mui/icons-material/NotificationsNoneOutlined';
import { useRecoilState } from 'recoil';
import { NoteAlarmAtom, accessTokenAtom, genderAtom, idAtom, nameAtom, refreshTokenAtom } from '../../Recoil/State';
import {  useNavigate } from 'react-router-dom';
import { RecentNoteModal } from '../../pages/User/ForgotIdPw/ForgotIdStyles';
import { useQuery } from '@tanstack/react-query';
import api from '../../apis/Api';
import { checkNote } from '../../apis/Response/Response';

const HeaderBack = styled.div`
    width: 100%;
    height: 10vh;
    background-color :rgba(255, 255, 255, 0.35) ;
    // opacity:0.35;
    display: flex;
    justify-content: space-between;
    align-items: center;
`;

function Greetings(){
  const [gender, setGender] = useRecoilState(genderAtom);
  const [accessToken, setAccessToken] = useRecoilState(accessTokenAtom);
  const [refreshToken, setRefreshToken] = useRecoilState(refreshTokenAtom);
  const [open, setOpen] = useRecoilState(NoteAlarmAtom);
  const [name,setName] = useRecoilState(nameAtom);
  const [Userid, setId] = useRecoilState(idAtom);
    const navigate=useNavigate();
    const GoResponseNote = () => {
        navigate("/note");
      };
    const GoLogOut =()=>{
      setGender(null);
      setAccessToken(null);
      setRefreshToken(null);
      setId(null);
      setName(null);
      navigate("/");
    }

    // const checknote = useQuery<checkNote>(['recentnote'], async () => {
    //     const response = await api.get("/note/recent/", {
    //       params: {
    //         id: Userid,
    //       },
    //     });
    //     return response.data; 
    //   });
      
    //   if (checknote.data) {
    //     const checknoteResult = checknote.data;
    //     setOpen(checknoteResult.result);
    //   }
      
    return(
        <div>
            <RecentNoteModal></RecentNoteModal>
            <HeaderBack>
                <Logo/>
                <div style={{display:'flex', alignItems:'center'}}>
                    {/* <Profile></Profile> */}
                    <div style={{fontSize:'large'}}>{name}님</div>
                    <IconButton color="primary" size="large" onClick={GoResponseNote}>
                        <EmailOutlinedIcon fontSize="large"/>
                    </IconButton>
                    <IconButton color="primary" size="large" >
                        <NotificationsNoneOutlinedIcon fontSize="large"/>
                    </IconButton>
                    <IconButton color="primary" size="large" onClick={GoLogOut}>
                        <LogoutOutlinedIcon fontSize="large"/>
                    </IconButton>
                </div>
            </HeaderBack>
        </div>
    );
}



export default Greetings;