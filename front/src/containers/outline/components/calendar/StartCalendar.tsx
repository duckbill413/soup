import { DemoContainer } from "@mui/x-date-pickers/internals/demo";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import { LocalizationProvider } from "@mui/x-date-pickers/LocalizationProvider";
import { DatePicker } from "@mui/x-date-pickers/DatePicker";
import dayjs, { Dayjs } from 'dayjs'
import { useMutation, useStorage } from '../../../../../liveblocks.config'

function StartCalendar () {
  const initialProject = useStorage((root) => root.outline)
  const updateProject = useMutation(({ storage }, key , newValue:Dayjs | null) => {
    const outline = storage.get("outline");
    outline?.set(key, newValue);
  }, []);

  return (
    <LocalizationProvider
      dateAdapter={AdapterDayjs}
      // dateFormats={datePickerUtils}
    >
      <DemoContainer
        components={["DatePicker"]}
      >
        <DatePicker
          label="시작일을 선택해주세요"
          slotProps={{
            textField: {
              size: "small",
            },
          }}
          format="YYYY / MM / DD"
          value={initialProject?.project_startDate ? dayjs(initialProject.project_startDate) : null}
          onChange={(newValue)=>updateProject('project_startDate', newValue)}
        />
      </DemoContainer>
    </LocalizationProvider>
  )
}

export default StartCalendar