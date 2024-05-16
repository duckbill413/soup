'use client'

import { buildProjectAPI, getBuildFileAPI } from '@/apis/build'
import * as styles from '@/containers/build/styles/generate.css'
import { elapsedTime } from '@/utils/elapsedTime'
import {
  Bolt,
  Description,
  Download,
  Folder,
  FolderOpen,
  Source,
} from '@mui/icons-material'
import CloseIcon from '@mui/icons-material/Close'
import { Button, Dialog, Slide } from '@mui/material'
import IconButton from '@mui/material/IconButton'
import Link from '@mui/material/Link'
import Toolbar from '@mui/material/Toolbar'
import Typography from '@mui/material/Typography'
import { TransitionProps } from '@mui/material/transitions'
import { SimpleTreeView, TreeItem } from '@mui/x-tree-view'
import { langs } from '@uiw/codemirror-extensions-langs'
import { vscodeDark } from '@uiw/codemirror-theme-vscode'
import CodeMirror from '@uiw/react-codemirror'
import { useParams } from 'next/navigation'
import { ReactElement, Ref, forwardRef, useState } from 'react'
import { useMutation, useStorage } from '../../../../liveblocks.config'
import { BuildResult } from '../types/buildFile'

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
  const [buildFile, setBuildFile] = useState<BuildResult | null>(null)

  const builtAt = useStorage((root) => root.build?.builtAt)
  const building = useStorage((root) => root.build?.building)

  const updateLiveblock = useMutation(({ storage }, key, value) => {
    const build = storage.get('build')
    build?.set(key, value)
  }, [])

  const params = useParams()
  const { projectId } = params

  const handleClickOpen = async () => {
    try {
      const res = await getBuildFileAPI(
        typeof projectId === 'string' ? projectId : projectId[0],
      )
      setBuildFile(res.result)
      updateLiveblock('builtAt', res.result.info.builtAt)
      setOpen(true)
    } catch (e) {
      console.error(e)
      alert('잠시 후 다시 시도해주세요.')
      setBuildFile(null)
      updateLiveblock('builtAt', null)
    }
  }

  const handleClickBuild = async () => {
    updateLiveblock('building', true)
    try {
      await buildProjectAPI(
        typeof projectId === 'string' ? projectId : projectId[0],
      )
      handleClickOpen()
    } catch (e) {
      console.error(e)
      alert('잠시 후 다시 시도해주세요.')
    }
    updateLiveblock('building', false)
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
            setCode({ code: entryArray[1][1] })
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
        onClick={handleClickBuild}
        disabled={building}
      >
        새로 빌드하기
      </Button>
      <Slide direction="left" in={!!builtAt} mountOnEnter unmountOnExit>
        <Button
          variant="outlined"
          endIcon={<Source />}
          size="large"
          onClick={handleClickOpen}
        >
          {`빌드 파일 확인 (${elapsedTime(builtAt ?? '')} 빌드됨)`}
        </Button>
      </Slide>
      {buildFile ? (
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
            {/* <a href={buildFile.info.s3Url} download> */}
            <Link
              href={buildFile.info.s3Url}
              underline="none"
              color="inherit"
              download
            >
              <Button
                autoFocus
                variant="contained"
                onClick={handleClose}
                endIcon={<Download />}
              >
                빌드 파일 다운로드
              </Button>
            </Link>
            {/* </a> */}
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
                {renderTree(buildFile?.build, 1, '')}
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
      ) : null}
    </div>
  )
}
