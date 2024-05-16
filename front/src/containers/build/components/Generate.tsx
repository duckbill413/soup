'use client'

import * as styles from '@/containers/build/styles/generate.css'
import { Bolt, Description, Folder, FolderOpen } from '@mui/icons-material'
import CloseIcon from '@mui/icons-material/Close'
import { Button, Dialog, Slide } from '@mui/material'
import IconButton from '@mui/material/IconButton'
import Toolbar from '@mui/material/Toolbar'
import Typography from '@mui/material/Typography'
import { TransitionProps } from '@mui/material/transitions'
import { SimpleTreeView, TreeItem } from '@mui/x-tree-view'
import { langs } from '@uiw/codemirror-extensions-langs'
import { vscodeDark } from '@uiw/codemirror-theme-vscode'
import CodeMirror from '@uiw/react-codemirror'
import { ReactElement, Ref, forwardRef, useState } from 'react'

// TODO: 샘플 데이터 삭제
const sampleData = {
  member: {
    api: {
      name: 'class name',
      package: 'package io.ssafy.soupapi.domain.jira.dao',
      data: 'package io.ssafy.soupapi.domain.member.api;\nimport io.ssafy.soupapi.global.common.code.SuccessCode;\nimport io.ssafy.soupapi.global.common.response.BaseResponse;\nimport lombok.RequiredArgsConstructor;\nimport lombok.extern.log4j.Log4j2;\nimport org.springframework.http.ResponseEntity;\nimport org.springframework.web.bind.annotation.RequestParam;\nimport org.springframework.web.bind.annotation.RestController;\n@Log4j2\n@RestController\n@RequiredArgsConstructor\npublic class MemberAPI {\n}',
    },
    application: {
      name: 'class name',
      package: 'package io.ssafy.soupapi.domain.jira.dao',
      data: 'package io.ssafy.soupapi.domain.member.api;\nimport io.ssafy.soupapi.global.common.code.SuccessCode;\nimport io.ssafy.soupapi.global.common.response.BaseResponse;\nimport lombok.RequiredArgsConstructor;\nimport lombok.extern.log4j.Log4j2;\nimport org.springframework.http.ResponseEntity;\nimport org.springframework.web.bind.annotation.RequestParam;\nimport org.springframework.web.bind.annotation.RestController;\n@Log4j2\n@RestController\n@RequiredArgsConstructor\npublic class MemberApplication {\n}',
    },
    dao: {
      name: 'class name',
      package: 'package io.ssafy.soupapi.domain.jira.dao',
      data: 'package io.ssafy.soupapi.domain.member.api;\nimport io.ssafy.soupapi.global.common.code.SuccessCode;\nimport io.ssafy.soupapi.global.common.response.BaseResponse;\nimport lombok.RequiredArgsConstructor;\nimport lombok.extern.log4j.Log4j2;\nimport org.springframework.http.ResponseEntity;\nimport org.springframework.web.bind.annotation.RequestParam;\nimport org.springframework.web.bind.annotation.RestController;\n@Log4j2\n@RestController\n@RequiredArgsConstructor\npublic class MemberController {\n}',
    },
    dto: {
      request: [
        {
          name: 'class name',
          package: 'package io.ssafy.soupapi.domain.jira.dao',
          data: 'package io.ssafy.soupapi.domain.member.api;\nimport io.ssafy.soupapi.global.common.code.SuccessCode;\nimport io.ssafy.soupapi.global.common.response.BaseResponse;\nimport lombok.RequiredArgsConstructor;\nimport lombok.extern.log4j.Log4j2;\nimport org.springframework.http.ResponseEntity;\nimport org.springframework.web.bind.annotation.RequestParam;\nimport org.springframework.web.bind.annotation.RestController;\n@Log4j2\n@RestController\n@RequiredArgsConstructor\npublic class MemberController {\n}',
        },
        {
          name: 'class name',
          package: 'package io.ssafy.soupapi.domain.jira.dao',
          data: 'package io.ssafy.soupapi.domain.member.api;\nimport io.ssafy.soupapi.global.common.code.SuccessCode;\nimport io.ssafy.soupapi.global.common.response.BaseResponse;\nimport lombok.RequiredArgsConstructor;\nimport lombok.extern.log4j.Log4j2;\nimport org.springframework.http.ResponseEntity;\nimport org.springframework.web.bind.annotation.RequestParam;\nimport org.springframework.web.bind.annotation.RestController;\n@Log4j2\n@RestController\n@RequiredArgsConstructor\npublic class MemberController {\n}',
        },
      ],
      response: [
        {
          name: 'class name',
          package: 'package io.ssafy.soupapi.domain.jira.dao',
          data: 'package io.ssafy.soupapi.domain.member.api;\nimport io.ssafy.soupapi.global.common.code.SuccessCode;\nimport io.ssafy.soupapi.global.common.response.BaseResponse;\nimport lombok.RequiredArgsConstructor;\nimport lombok.extern.log4j.Log4j2;\nimport org.springframework.http.ResponseEntity;\nimport org.springframework.web.bind.annotation.RequestParam;\nimport org.springframework.web.bind.annotation.RestController;\n@Log4j2\n@RestController\n@RequiredArgsConstructor\npublic class MemberController {\n}',
        },
        {
          name: 'class name',
          package: 'package io.ssafy.soupapi.domain.jira.dao',
          data: 'package io.ssafy.soupapi.domain.member.api;\nimport io.ssafy.soupapi.global.common.code.SuccessCode;\nimport io.ssafy.soupapi.global.common.response.BaseResponse;\nimport lombok.RequiredArgsConstructor;\nimport lombok.extern.log4j.Log4j2;\nimport org.springframework.http.ResponseEntity;\nimport org.springframework.web.bind.annotation.RequestParam;\nimport org.springframework.web.bind.annotation.RestController;\n@Log4j2\n@RestController\n@RequiredArgsConstructor\npublic class MemberController {\n}',
        },
      ],
    },
    entity: {
      name: 'class name',
      package: 'package io.ssafy.soupapi.domain.jira.dao',
      data: 'package io.ssafy.soupapi.domain.member.api;\nimport io.ssafy.soupapi.global.common.code.SuccessCode;\nimport io.ssafy.soupapi.global.common.response.BaseResponse;\nimport lombok.RequiredArgsConstructor;\nimport lombok.extern.log4j.Log4j2;\nimport org.springframework.http.ResponseEntity;\nimport org.springframework.web.bind.annotation.RequestParam;\nimport org.springframework.web.bind.annotation.RestController;\n@Log4j2\n@RestController\n@RequiredArgsConstructor\npublic class MemberController {\n}',
    },
  },
  board: {
    api: {
      name: 'class name',
      package: 'package io.ssafy.soupapi.domain.jira.dao',
      data: 'package io.ssafy.soupapi.domain.member.api;\nimport io.ssafy.soupapi.global.common.code.SuccessCode;\nimport io.ssafy.soupapi.global.common.response.BaseResponse;\nimport lombok.RequiredArgsConstructor;\nimport lombok.extern.log4j.Log4j2;\nimport org.springframework.http.ResponseEntity;\nimport org.springframework.web.bind.annotation.RequestParam;\nimport org.springframework.web.bind.annotation.RestController;\n@Log4j2\n@RestController\n@RequiredArgsConstructor\npublic class MemberController {\n}',
    },
    application: {},
    dao: {},
    dto: {
      request: [],
      response: [],
    },
    entity: {},
  },
}

const Transition = forwardRef(
  (
    props: TransitionProps & {
      children: ReactElement
    },
    ref: Ref<unknown>,
  ) => <Slide direction="up" ref={ref} {...props} />,
)

export default function Generate() {
  const [open, setOpen] = useState(false)
  const [code, setCode] = useState({ code: '' })

  const handleClickOpen = () => {
    setOpen(true)
  }

  const handleClose = () => {
    setCode({ code: '' })
    setOpen(false)
  }

  const renderTree = (
    fileObj: object,
    nth: number,
    id: string,
  ): JSX.Element | (JSX.Element | null)[] => {
    const entryArray = Object.entries(fileObj)
    const itemId = id + entryArray[0][0] + nth

    if (nth === 4)
      return entryArray.map((value, idx) => (
        <>{renderTree(value[1], nth + 1, itemId + idx)}</>
      ))

    if (typeof entryArray[0][1] === 'string') {
      return (
        <TreeItem
          itemId={itemId + entryArray[0][1]}
          label={entryArray[0][1]}
          key={itemId + entryArray[0][1]}
          onClick={() => {
            setCode({ code: entryArray[2][1] })
          }}
        />
      )
    }

    const temp = entryArray.map(([key, value]) => {
      if (
        (key === 'dto' &&
          Object.values(value.request).length === 0 &&
          Object.values(value.response).length === 0) ||
        Object.values(value).length <= 0
      )
        return null
      return (
        <TreeItem itemId={itemId + key} label={key} key={itemId + key}>
          {renderTree(value, nth + 1, itemId + key)}
        </TreeItem>
      )
    })
    return temp
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
            미리보기
          </Typography>
          <Button autoFocus color="inherit" onClick={handleClose}>
            빌드 파일 다운로드
          </Button>
        </Toolbar>
        <section className={styles.section}>
          <div className={`${styles.box} ${styles.file}`}>
            <SimpleTreeView
              sx={{
                flexGrow: 1,
                maxWidth: 400,
                overflowY: 'auto',
                padding: '12px',
              }}
              slots={{
                expandIcon: Folder,
                collapseIcon: FolderOpen,
                endIcon: Description,
              }}
            >
              {renderTree(sampleData, 1, '')}
            </SimpleTreeView>
          </div>
          <div className={`${styles.box} ${styles.code}`}>
            <CodeMirror
              editable={false}
              value={code.code}
              height="calc(80vh - 2px)"
              basicSetup={{
                foldGutter: false,
                dropCursor: false,
                allowMultipleSelections: false,
                indentOnInput: false,
              }}
              style={{
                flex: '1 0 0',
                borderRadius: '10px',
                outline: 'none',
                overflow: 'auto',
                border: '1px solid #dedede',
              }}
              theme={vscodeDark}
              extensions={[langs.java()]}
            />
          </div>
        </section>
      </Dialog>
    </div>
  )
}
