import { DemoContainer } from "@mui/x-date-pickers/internals/demo";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import { LocalizationProvider } from "@mui/x-date-pickers/LocalizationProvider";
import { DatePicker } from "@mui/x-date-pickers/DatePicker";
// import dayjs from 'dayjs'

function EndCalendar () {
  // const datePickerFormat = "YYYY-MM-DD";
  // const datePickerUtils = {
  //   format: datePickerFormat,
  //   parse: (value:any) => dayjs(value, datePickerFormat, true).toDate(),
  // };
  return (
    <LocalizationProvider
      dateAdapter={AdapterDayjs}
      // dateFormats={datePickerUtils}
    >
      <DemoContainer
        components={["DatePicker"]}
      >
        <DatePicker
          label="종료일을 선택해주세요"
          slotProps={{
            textField: {
              size: "small",
            },
          }}
          format="YYYY / MM / DD"
          // value={startDate}
          // onChange={(newValue) => {
          //   startDateChange(
          //     newValue
          //   );
          // }}
        />
      </DemoContainer>
    </LocalizationProvider>
  )
}

export default EndCalendar