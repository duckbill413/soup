'use client'

import * as styles from '@/containers/build/generate.css'
import { Bolt, Folder } from '@mui/icons-material'
import CloseIcon from '@mui/icons-material/Close'
import { Button, Dialog, Slide } from '@mui/material'
import IconButton from '@mui/material/IconButton'
import Toolbar from '@mui/material/Toolbar'
import Typography from '@mui/material/Typography'
import { TransitionProps } from '@mui/material/transitions'
import { langs } from '@uiw/codemirror-extensions-langs'
import CodeMirror from '@uiw/react-codemirror'
import * as React from 'react'

const sampleData = [
  {
    id: '1',
    name: 'Board',
    isDirectory: true,
    data: [
      {
        id: '1-1',
        name: 'api',
        isDirectory: true,
        data: [
          {
            id: 1,
            name: 'BoardController.java',
            isDirectory: false,
            data: `package io.ssafy.soupapi.domain.member.api;

            import io.ssafy.soupapi.domain.member.application.MemberService;
            import io.ssafy.soupapi.global.common.code.SuccessCode;
            import io.ssafy.soupapi.global.common.response.BaseResponse;
            import io.ssafy.soupapi.global.security.TemporalMember;
            import jakarta.validation.constraints.NotEmpty;
            import lombok.RequiredArgsConstructor;
            import lombok.extern.log4j.Log4j2;
            import org.springframework.http.ResponseEntity;
            import org.springframework.security.core.annotation.AuthenticationPrincipal;
            import org.springframework.web.bind.annotation.RequestParam;
            import org.springframework.web.bind.annotation.RestController;
            
            @Log4j2
            @RestController
            @RequiredArgsConstructor
            public class MemberController {
                private final MemberService memberService;
                public ResponseEntity<BaseResponse<String>> updateNickname(
                        @NotEmpty(message = "닉네임을 확인해 주세요")
                        @RequestParam("nickname") String nickname,
                        @AuthenticationPrincipal TemporalMember member
                ) {
                    return BaseResponse.success(
                            SuccessCode.UPDATE_SUCCESS,
                            memberService.updateNickname(nickname, member)
                    );
                }
            }
            
            `,
          },
        ],
      },
      {
        id: '1-2',
        name: 'dao',
        isDirectory: true,
        data: [
          {
            id: '1-2-1',
            name: 'BoardRepository.java',
            isDirectory: false,
            data: `empty`,
          },
        ],
      },
    ],
  },
]

const Transition = React.forwardRef(
  (
    props: TransitionProps & {
      children: React.ReactElement
    },
    ref: React.Ref<unknown>,
  ) => <Slide direction="up" ref={ref} {...props} />,
)

export default function Generate() {
  const [open, setOpen] = React.useState(false)

  const handleClickOpen = () => {
    setOpen(true)
  }

  const handleClose = () => {
    setOpen(false)
  }

  const handleFolder = (item: any) => {
    alert(`click ${item}`)
  }

  return (
    <div className={styles.container}>
      <Button
        variant="contained"
        endIcon={<Bolt />}
        size="large"
        onClick={handleClickOpen}
      >
        Generate
      </Button>
      <Dialog
        fullScreen
        open={open}
        onClose={handleClose}
        TransitionComponent={Transition}
      >
        <Toolbar>
          <IconButton
            edge="start"
            color="inherit"
            onClick={handleClose}
            aria-label="close"
          >
            <CloseIcon />
          </IconButton>
          <Typography sx={{ ml: 2, flex: 1 }} variant="h6" component="div">
            Preview
          </Typography>
          <Button autoFocus color="inherit" onClick={handleClose}>
            download
          </Button>
        </Toolbar>
        <section className={styles.section}>
          <div className={`${styles.box} ${styles.file}`}>
            {sampleData.map((item) => (
              <div
                key={item.id}
                className={styles.list}
                onClick={() => handleFolder(item)}
                onKeyDown={handleFolder}
                role="presentation"
              >
                <Folder />
                <span className={styles.name}>{item.name}</span>
              </div>
            ))}
          </div>
          <div className={`${styles.box} ${styles.code}`}>
            <CodeMirror
              editable={false}
              value="console.log('hello world!');"
              height="80vh"
              basicSetup={{
                foldGutter: false,
                dropCursor: false,
                allowMultipleSelections: false,
                indentOnInput: false,
              }}
              extensions={[langs.java()]}
            />
          </div>
        </section>
      </Dialog>
    </div>
  )
}
