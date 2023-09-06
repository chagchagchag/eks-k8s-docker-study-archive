import React, { Fragment } from 'react'
import {useEffect, useState} from 'react'
import { IntroContainerStyle } from './styles/IntroContinerStyle.style'
import { IntroHeaderStyle, IntroMainHeaderStyle } from './styles/IntroHeaderStyle.style'
import './styles/style.css';
import axios from 'axios';
import {global} from '../config/config.default';

const Intro = () => {
	
  let[backendMsg, setBackendMsg] = useState('(백엔드에서도 반갑데요!! >>> (메시지 호출중))')
	
  useEffect(() => {
	  console.log('컴포넌트 로딩');
	  console.log('env.baseUrl = ' + (global.env.baseUrl || '헐'));
	  fetchMessage();
  });

  const fetchMessage = () => {
	axios
		.get('http://localhost:8080/')
		.then((resp) => {
			console.log(resp.data)
			setBackendMsg(resp.data);
		})
		.catch((err) => {console.log(err)});
  };


  return (  
	<IntroContainerStyle>
		<IntroHeaderStyle>
			<IntroMainHeaderStyle>
				<h1 className='font-white'> Hello World </h1>
				<h5 className='font-white'> Frontend 앱의 Intro 페이지입니다!! </h5>
				<h5 className='font-white'> 반갑습니다. (백엔드에서 온 메시지 = {backendMsg})</h5>
			</IntroMainHeaderStyle>
		</IntroHeaderStyle>
	</IntroContainerStyle>
  )
}



export default Intro